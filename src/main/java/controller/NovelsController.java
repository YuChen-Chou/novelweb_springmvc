package controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exception.DaoException;
import model.Chapter;
import model.Classification;
import model.Member;
import model.Novels;
import model.PageInfoBean;
import model.Slideshow;
import myutil.FileUploadPath;
import services.IMemberService;
import services.INovelService;

@Controller
@RequestMapping("novels")
public class NovelsController {
	
	@Autowired
	private IMemberService memberService;

	@Autowired
	private INovelService novelService;

	/**
	 * 小說網首頁:返回最新的小說list (依照更新時間:新->舊)
	 * @param model
	 * @param classid 分類id(非必要)
	 * @param searchString 搜尋字串(非必要)
	 * @param pageNum 當前頁碼(非必要)
	 * @param session
	 * @return
	 */
	@GetMapping("index")
	public String Index(Model model, @RequestParam(value = "classid", required = false) String classid,
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			HttpSession session) {

		// 分類清單
		List<Classification> classification = novelService.findAllNovelsClassification();
		session.setAttribute("nav_novel_classification", classification);
		// 當天日期
		model.addAttribute("now", new Date());

		// 新書推薦榜
		List<Novels> newNovels = novelService.findNewNovelsTop4();
		model.addAttribute("new_novels_list", newNovels);
		
		//幻燈片
		List<Slideshow> slideshows = novelService.findAllEnableSlideshowList();
		model.addAttribute("slideshows_list", slideshows);
		
		
		// 分類List區塊-> 查找該分類小說前8筆
		if (classid != null) {
			String classname = novelService.findNovelsClassification(Integer.valueOf(classid)).getName();
			model.addAttribute("classname", classname);
			model.addAttribute("classid", classid);
			// 依照分類顯示小說list
			try {
				// 分類封裝
				PageInfoBean<Novels> novels = novelService.findNovelsByClassIdByPage(classid, pageNum);
				model.addAttribute("pageInfo", novels);
			} catch (DaoException e) {
				model.addAttribute("errinfo", e.getMessage());
				System.out.println("errinfo:" + e.getMessage());
			}

		} else if (searchString != null && searchString != "") {
			try {
				PageInfoBean<Novels> novels = novelService.findNovelsBySearchStringByPage(searchString, pageNum);
				model.addAttribute("pageInfo", novels);
				model.addAttribute("searchString", searchString);
				System.out.println("searchString 搜尋:"+searchString);
			} catch (DaoException e) {
				model.addAttribute("errinfo", e.getMessage());
				System.out.println("errinfo:" + e.getMessage());
			}
		} else {
			// 預設小說list
			try {
				// 改為分類封裝
				PageInfoBean<Novels> novels = novelService.findNewAllByPage(pageNum);
				model.addAttribute("pageInfo", novels);
			}catch (DaoException e) {
				model.addAttribute("errinfo", e.getMessage());
			}
		}

		return "novelpage/index";
	}

	/**
	 * 點選導航分類->返回該分類的小說list (依照更新時間:新->舊)
	 * 
	 * @param classid
	 * @param attr
	 * @return
	 */
	@RequestMapping("classlist/{classid}")
	public String Classlist(@PathVariable("classid") String classid, RedirectAttributes attr) {
		// 透過GET重定向到index頁面，再index頁面使用@RequestParam抓取到資料
		attr.addAttribute("classid", classid);
		return "redirect:../index";
	}

	/**
	 * 點選小說->透過小說id返回該小說資訊及章節list
	 * 
	 * @param novelid
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("novelinfo/{novelid}")
	public String NovelInfo(@PathVariable("novelid") String novelid, Model model,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,HttpSession session) {

		System.out.println("novelid：" + novelid);
		// 查找該小說id的全部章節
		Novels novel = novelService.findById(Integer.valueOf(novelid));
		model.addAttribute("novel", novel);

		List<Chapter> chapters = novelService.findAllChapterByNovel(novel);
		model.addAttribute("chapters", chapters);
		
		// 如果登入會員，查找該會員是否已添加此書為收藏
		if(session.getAttribute("member")!=null) {
			Member member = (Member)session.getAttribute("member");
			if(memberService.checkFavoritesByMember(member, Integer.valueOf(novelid))) {
				//如果已存在
				model.addAttribute("memberFav","已收藏");
			}
			
		}

		return "novelpage/novelInfo";
	}

	/**
	 * 點選章節->章節內容(分頁封裝)
	 * 
	 * @param novelid
	 * @param pageNum 頁面號碼
	 * @param model
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("novel/{novelid}/page/{pageNum}")
	public String Chapter(@PathVariable("novelid") String novelid, @PathVariable("pageNum") Integer pageNum,
			Model model, HttpServletRequest request) throws UnsupportedEncodingException {

		// 使用分頁封裝
		PageInfoBean<Chapter> p_chapter = novelService.findChaptersByNovelIdByPage(novelid, pageNum);
		System.out.println(p_chapter.getPageNum() + ";" + p_chapter.getPageSize());

		// 將txt文字內容設定回Chapter的Content欄位
		for (Chapter chapter : p_chapter.getList()) {
			chapter.setContent(new FileUploadPath().readChapterPathByBr(chapter, request));
		}
		model.addAttribute("p_chapter", p_chapter);

		return "novelpage/chapter";
	}
	/**
	 * 搜尋書名或作者
	 * @param searchString
	 * @param attr
	 * @return
	 */
	@RequestMapping("search")
	public String search(@RequestParam("search_string") String searchString, RedirectAttributes attr) {
		System.out.println("搜尋:" + searchString);
		searchString = searchString.trim();// 移除開頭或結尾的空白
		attr.addAttribute("searchString", searchString);
		return "redirect:index";
	}

}
