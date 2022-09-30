package controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exception.DaoException;
import model.Author;
import model.Chapter;
import model.Classification;
import model.Favorites;
import model.Member;
import model.Novels;
import myutil.FileUploadPath;
import services.IMemberService;
import services.INovelService;
import validation.ValidAdd;
import validation.ValidLogin;
import validation.ValidUpdate;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private INovelService novelService;
	
	/***
	 * 會員註冊
	 * @param member
	 * @param br
	 * @param session
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addMember(@Validated(value = {ValidAdd.class})@ModelAttribute("Member") Member member,BindingResult br ,HttpSession session,RedirectAttributes attr) {
		
		System.out.println("帳號:"+member.getUsername());
		System.out.println("密碼:"+member.getPassword());
		System.out.println("名稱:"+member.getName());
		System.out.println("信箱:"+member.getEmail());
		System.out.println("電話:"+member.getPhone());
		System.out.println("性別:"+member.getGender());
		
		String errMsg="";
		if(!br.hasErrors()) {
			try {
				memberService.addMember(member);
				attr.addFlashAttribute("success", "註冊成功");
			} catch (DaoException e) {
				errMsg=e.getMessage();
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
			
		}
		attr.addFlashAttribute("errMsg", errMsg);

		return "redirect:../novels/index";
	}
	
	/**
	 * 會員登入
	 * @param member
	 * @param br
	 * @param session
	 * @param attr
	 * @return 注意redirect:後面不能加空格，否則attr.addFlashAttribute值就無法傳遞出去
	 */
	@PostMapping(value = "login")
	public String login(@Validated(value = {ValidLogin.class}) Member member,BindingResult br ,HttpSession session,RedirectAttributes attr) {
		System.out.println("登入:"+member.getUsername());
		String errMsg="";
		if(!br.hasErrors()) {
			Member m=null;
			try {
				m=memberService.login(member.getUsername(), member.getPassword());
				session.setAttribute("member", m);
			}catch (DaoException e) {
				errMsg=e.getMessage();
			}
			
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}			
		}
		attr.addFlashAttribute("errMsg", errMsg);

		return "redirect:../novels/index";
	}
	
	/**
	 * 會員登出
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		System.out.println("===session:"+session.getId());
		if(session!=null) {
			session.invalidate();
			System.out.println("===session invalidate===");
		}

		return "redirect:../novels/index";
	}
	
	/**
	 * 會員個人資訊頁面
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String memberInfo(HttpSession session) {
		if(session.getAttribute("member")==null) {
			return "redirect:../novels/index";
		}
		return "memberpage/memberinfo";
	}
	
	
	/**
	 * 會員資料修改
	 * @param session
	 * @param member
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String memberUpdate(HttpSession session,@Validated(value = {ValidUpdate.class})@ModelAttribute("Member") Member member,BindingResult br,RedirectAttributes attr) {
		
		System.out.println("=====會員Update=====");
		System.out.println("會員ID"+member.getId());
		System.out.println("會員名"+member.getName());
		System.out.println("會員帳號"+member.getUsername());
		System.out.println("會員密碼"+member.getPassword());
		System.out.println("會員性別"+member.getGender());
		System.out.println("會員電話"+member.getPhone());
		System.out.println("會員信箱"+member.getEmail());
		String errMsg="";
		if(!br.hasErrors()) {
			try {
				memberService.updateMember(member);
				session.setAttribute("member", member);
				attr.addFlashAttribute("success", "修改成功");
			} catch (DaoException e) {
				errMsg=e.getMessage();
			}
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);
		
		return "redirect:info";
	}
	
	/**
	 * 作者管理頁面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "authorpage", method = RequestMethod.GET)
	public String authorApply(HttpSession session,Model model) {
		
		if(session.getAttribute("member")==null) {
			return "redirect:../novels/index";
		}
		
		Member member = (Member)session.getAttribute("member");
		System.out.println("=====會員申請作者頁面=====");
		System.out.println(((Member)session.getAttribute("member")).getMemberclass());
		
		//判斷是作者會員
		if(memberService.isAuthor(member.getId())) {
			System.out.println("===作者===");
			//傳回該作者id的小說list
			//1.先找到作者id
			try {
				Author author = memberService.findAuthorByMemberId(member.getId());
				//2.透過novelService找list
				List<Novels> novels = novelService.findNovelsByAuthorId(author.getId());
				List<Classification> classification = novelService.findAllNovelsClassification();
				model.addAttribute("author", author);
				model.addAttribute("novels", novels);
				model.addAttribute("classification", classification);
				
			} catch (DaoException e) {
				System.out.println("錯誤:"+e.getMessage());
			}
			
		}
		
		return "memberpage/authorpage";
	}
	
	
	/**
	 * 新增作者(申請作者)
	 * @param session
	 * @param author
	 * @param br
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "authorpage", method = RequestMethod.POST)
	public String authorApply(HttpSession session,@Validated(value = {ValidAdd.class})@ModelAttribute("Author")Author author,BindingResult br,RedirectAttributes attr) {
		Member member = (Member)session.getAttribute("member");
		
		System.out.println("=====會員申請作者=====");
		System.out.println(member.getId());
		System.out.println(author.getName());
		String errMsg="";
		if(!br.hasErrors()) {
			//呼叫申請作者services
			try {
				member=memberService.authorRegist(member,author.getName());
				session.setAttribute("member", member);
				attr.addFlashAttribute("success", "申請成功");
				attr.addFlashAttribute("successMsg", "申請成功");
			}catch (DaoException e) {
				errMsg = e.getMessage();
			}
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);

		return "redirect:authorpage";
	}
	
	/**
	 * 新增小說
	 * @param session
	 * @param novels
	 * @param br
	 * @param attr
	 * @param imgfile
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "addNovel", method = RequestMethod.POST)
	public String addNovel(HttpSession session,@Validated(value = {ValidAdd.class}) Novels novels,BindingResult br ,RedirectAttributes attr, MultipartFile imgfile,HttpServletRequest request) throws IllegalStateException, IOException {

		System.out.println("=====新增小說=====");
		//小說物件-傳來書名+介紹+分類	
		System.out.println("novels:"+novels.getName());
		System.out.println("novels:"+novels.getAuthor().getId());
		System.out.println("novels:"+novels.getAuthor().getName());
		System.out.println("novels:"+novels.getIntroduction());
		System.out.println("novels:"+novels.getClassification().getId());
		System.out.println("novels:"+imgfile);
		
		String errMsg="";
		if(!br.hasErrors()) {
			String imgPath=new FileUploadPath().getImgLoadPath(novels, imgfile, request);
			//呼叫申請作者services
			try {
				novelService.addNovels(novels,imgPath);
			}catch (DaoException e) {
				errMsg = e.getMessage();
			}
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);
		
		return "redirect:authorpage";
	}

	/**
	 * 更新小說管理頁面
	 * @param id
	 * @param novels
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateNovel/{id}", method = RequestMethod.GET)
	public String updateNovel(@PathVariable("id")Integer id,Novels novels ,HttpSession session,Model model,HttpServletRequest request) {
		if(session.getAttribute("member")==null) {
			return "redirect:../../novels/index";
		}

		novels = novelService.findById(id);
		String dt=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(novels.getUpdatetime().getTime());
		List<Classification> classification = novelService.findAllNovelsClassification();
		Member member = (Member)session.getAttribute("member");
		Author author = memberService.findAuthorByMemberId(member.getId());
		List<Chapter> chapters=new ArrayList<Chapter>();
		try {
			chapters=novelService.findAllChapterByNovel(novels);
			for(Chapter c:chapters) {
				//將txt文字內容設定回Chapter的Content欄位
				c.setContent(new FileUploadPath().readChapterPath(c, request));
			}
		} catch (Exception e) {
			System.out.println("updateNovel Error:"+e.getMessage());
		}
		
		
		model.addAttribute(novels);
		model.addAttribute("author", author);
		model.addAttribute("classification", classification);
		model.addAttribute("dt",dt);
		model.addAttribute("chapters",chapters);
		
		return "memberpage/updateNovel";
	}
	
	
	/**
	 * 更新書名、類別、介紹區塊
	 * @param id
	 * @param attr
	 * @param novels
	 * @param br
	 * @param imgfile
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "updateNovel/{id}", method = RequestMethod.POST)
	public String updateNovel(@PathVariable("id")Integer id,RedirectAttributes attr,@Validated(value = {ValidUpdate.class}) Novels novels,BindingResult br, MultipartFile imgfile,HttpServletRequest request) throws IllegalStateException, IOException {
		System.out.println("=====修改小說=====");
		System.out.println("novels:"+novels.getName());
		System.out.println("novels:"+novels.getAuthor().getId());
		System.out.println("novels:"+novels.getIntroduction());
		System.out.println("novels:"+novels.getClassification().getId());
		
		String errMsg="";
		if(!br.hasErrors()) {
			//得到Server圖片路徑
			String imgPath=new FileUploadPath().getImgLoadPath(novels, imgfile, request);
			novelService.updateNovels(novels,imgPath);
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);
		return "redirect:../updateNovel/{id}";
	}
	
	/**
	 * 新增章節
	 * @param id
	 * @param attr
	 * @param novels
	 * @param chapter
	 * @param br
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "addChapter/{id}", method = RequestMethod.POST)
	public String addChapter(@PathVariable("id")Integer id,RedirectAttributes attr, Novels novels,@Validated(value = {ValidAdd.class}) Chapter chapter,BindingResult br,HttpServletRequest request) throws UnsupportedEncodingException {
		
		String errMsg="";
		if(!br.hasErrors()) {
			//得到Server章節txt路徑
			String chapterPath=new FileUploadPath().saveChapterPath(chapter, request);
			novelService.addChapter(chapter,chapterPath);
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);
		
		return "redirect:../updateNovel/{id}";
	}
	
	/**
	 * 修改章節
	 * @param id
	 * @param attr
	 * @param session
	 * @param novels
	 * @param chapter
	 * @param br
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "updateChapter/{id}", method = RequestMethod.POST)
	public String updateChapter(@PathVariable("id")Integer id,RedirectAttributes attr,HttpSession session, Novels novels,@Validated(value = {ValidUpdate.class}) Chapter chapter,BindingResult br,HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("=====修改小說章節=====");
		System.out.println("novels:"+chapter.getId());
		System.out.println("novels:"+chapter.getName());
		System.out.println("novels:"+chapter.getContent());
		System.out.println("novels:"+chapter.getNovels().getId());
		System.out.println("novels:"+novels.getId());
		
		String errMsg="";
		if(!br.hasErrors()) {
			new FileUploadPath().updateChapter(chapter, request);
			novelService.updateChapter(chapter);
		} else {
			for (FieldError fieldError : br.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
		}
		attr.addFlashAttribute("errMsg", errMsg);
		
		return "redirect:../updateNovel/{id}";
	}
	/**
	 * 收藏按鈕:添加收藏
	 * @param novelid
	 * @return
	 */
	@RequestMapping(value = "addFavorites/{novelid}")
	public String addFavorites(@PathVariable("novelid")Integer novelid,HttpSession session) {
		System.out.println("novelid:"+novelid);
		Member member = (Member)session.getAttribute("member");
	
		if(member!=null) {
			System.out.println("會員存在");
			memberService.addFavorites(member, novelid);
		}
		
		return "redirect:../../novels/novelinfo/{novelid}";
	}
	/**
	 * 收藏按鈕:添加收藏
	 * @param novelid
	 * @return 
	 */
	@RequestMapping(value = "deleteFavorites/{favoritesid}")
	public String deleteFavorites(@PathVariable("favoritesid")Integer favoritesid,HttpSession session) {
		System.out.println("favoritesid:"+favoritesid);
		Member member = (Member)session.getAttribute("member");
		//TODO:收藏服務
		if(member!=null)
			memberService.deleteFavorites(favoritesid);
		
		return "redirect:../favorites";
	}
	
	/**
	 * 個人收藏頁面
	 * @param session
	 * @return favoritemanage.jsp
	 */
	@RequestMapping(value = "favorites")
	public String favoritesManage(HttpSession session,Model model) {
		if(session.getAttribute("member")==null) {
			return "redirect:../novels/index";
		}
		Member member = (Member)session.getAttribute("member");
		System.out.println("會員名:"+member.getName());
		//查詢收藏 返回list
		List<Favorites> favorites=memberService.findFavorites(member);
		model.addAttribute("favorites", favorites);
		
		return "memberpage/favoritemanage";
	}
	

}
