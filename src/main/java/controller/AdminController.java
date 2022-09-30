package controller;


import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exception.DaoException;
import model.Admin;
import model.Classification;
import model.Slideshow;
import myutil.FileUploadPath;
import services.IAdminService;
import services.INovelService;
import validation.ValidAdd;
import validation.ValidLogin;
import validation.ValidUpdate;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private INovelService novelService;
	
	
	/*===========================*/
	/*     管理員登入頁面相關方法              */
	/*===========================*/
	
	/**
	 * 管理員登入頁面
	 * @param model-用於adminlogin.jsp中modelAttribute="admin"的key值
	 * @return adminlogin.jsp
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(new Admin());
		return "adminpage/adminlogin";
	}
	

	
	/**
	 * 登出帳號
	 * @param session
	 * @return redirect:login
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		System.out.println("===session:"+session.getId());
		if(session!=null) {
			session.invalidate();
			System.out.println("===session invalidate===");
		}
		
		return "redirect:login";
	}
	
	/**
	 * 管理員登入頁面-表單驗證
	 * @param admin-管理員帳號密碼資料
	 * @param br-驗證的結果-失敗返回adminpage/adminlogin
	 * @param session-將登入的管理員以及管理員list放入session傳送
	 * @param model-放錯誤訊息
	 * @return redirect:adminManager
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@Validated(value = {ValidLogin.class}) Admin admin,BindingResult br ,HttpSession session,Model model) {
		
		if(!br.hasErrors()) {
			Admin a=null;
			try {
				a=adminService.login(admin.getUsername(), admin.getPassword());
				session.setAttribute("admin", a);
				return "redirect:adminManager";
			}catch (DaoException e) {
				model.addAttribute("errloginMsg", e.getMessage());
			}
		}
		return "adminpage/adminlogin";
	}
	
	
	/**
	 * 顯示登入後首頁面
	 * @param session-放置登入資訊
	 * @param model-放置資料
	 * @param request-請求
	 * @return adminpage/showadminlist.jsp
	 */
	@RequestMapping(value = "adminManager", method = RequestMethod.GET)
	public String adminManager(HttpSession session,Model model) {
		if(session.getAttribute("admin")==null) {
			return "redirect:login";
		}
		model.addAttribute("adminlist", adminService.findAllAdmin());	
		//點選幻燈片導航顯示
		return "adminpage/showadminlist";
	}
	
	
	/**
	 * 新增管理員
	 * @param admin
	 * @param br
	 * @param session
	 * @param attr
	 * @return redirect:adminManager
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addAdmin(@Validated(value = {ValidAdd.class})@ModelAttribute("Admin") Admin admin,BindingResult br ,RedirectAttributes attr) {
		System.out.println("添加管理員");
		String errAddMsg="";
		if(!br.hasErrors()) {
			try {
				adminService.addAdmin(admin);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg", e.getMessage());
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errAddMsg += fieldError.getDefaultMessage()+"<br>";
			}
			attr.addFlashAttribute("errMsg", errAddMsg);
		}

		return "redirect:adminManager";
	}
	

	/**
	 * 查詢-帳號
	 * @param admin
	 * @param attr
	 * @return redirect:adminManager
	 */
	@RequestMapping(value = "find", method = RequestMethod.POST)
	public String findAdmin(@ModelAttribute("Admin") Admin admin,RedirectAttributes attr) {

			Admin a = null;
			try {
				a = adminService.queryAdmin(admin.getUsername());
				attr.addFlashAttribute("adminfind", a);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
			}
					
			return "redirect:adminManager";
	}	
	
	/**
	 * 更新管理員資料
	 * @param admin
	 * @param br
	 * @param attr
	 * @return redirect:adminManager
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateAdmin(@Validated(value = {ValidUpdate.class})@ModelAttribute("Admin") Admin admin,BindingResult br ,RedirectAttributes attr) {
		String errUpdateMsg="";
		if(!br.hasErrors()) {
			try {
				adminService.updateAdmin(admin);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
				System.out.println("錯誤訊息: "+e.getMessage());
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errUpdateMsg += fieldError.getDefaultMessage()+"<br>";
			}
			attr.addFlashAttribute("errMsg", errUpdateMsg);
		}
	
		return "redirect:adminManager";
	}	
	
	
	/**
	 * 刪除管理員資料
	 * @param admin
	 * @param session
	 * @param model
	 * @return redirect:../adminManager
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteAdmin(@PathVariable("id")Integer id,HttpSession session,RedirectAttributes attr) {
		if(session.getAttribute("admin")==null) {
			return "redirect:login";
		}
		//不能刪除自己
		Admin admin=(Admin)session.getAttribute("admin");//當前session登入的使用者
		Admin delAdmin=adminService.findById(id);
		if(!admin.getUsername().equals(delAdmin.getUsername())) {
			try {
				adminService.deleteAdmin(delAdmin);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg", e.getMessage());
			}
		}else {
			attr.addFlashAttribute("errMsg", "不能刪除自己");
		}
		
		return "redirect:../adminManager";
	}	
	

	
	
	/*===========================*/
	/*     小說管理頁面相關方法                 */
	/*===========================*/
	
	/**
	 * 顯示小說管理頁面
	 * @param model
	 * @return novelwebManager.jsp
	 */
	@RequestMapping(value = "novelwebManager", method = RequestMethod.GET)
	public String novelwebManager(Model model,HttpSession session, RedirectAttributes attr) {
		if(session.getAttribute("admin")==null) {
			return "redirect:login";
		}
		List<Classification> classification = novelService.findAllNovelsClassification();
		model.addAttribute("classification", classification);
		
		return "adminpage/novelwebManager";
	}
	
	
	
	/**
	 * 新增小說分類
	 * @param classification
	 * @param attr
	 * @return redirect:novelwebManager
	 */
	@RequestMapping(value = "addNovelClass", method = RequestMethod.POST)
	public String addNovelClass(@Validated(value = {ValidAdd.class})@ModelAttribute("Classification") Classification classification,RedirectAttributes attr) {
		System.out.println("addNovelClass classification"+classification.getName());
		try {
			novelService.addClassification(classification);
			attr.addFlashAttribute("success", "新增成功");
		} catch (DaoException e) {
			attr.addFlashAttribute("errMsg", e.getMessage());
		}
		
		return "redirect:novelwebManager";
	}
	
	
	/**
	 * 查詢小說分類
	 * @param classification
	 * @param attr
	 * @return redirect:novelwebManager
	 */
	@RequestMapping(value = "classfind", method = RequestMethod.POST)
	public String findClass(@ModelAttribute("Classification") Classification classification,RedirectAttributes attr) {

			Classification q_classification;
			try {
				q_classification = novelService.findNovelsClassification(classification.getId());
				attr.addFlashAttribute("q_classification", q_classification);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
			}
					
			
			return "redirect:novelwebManager";
	}	

	
	/**
	 * 修改小說分類名稱
	 * @param classification
	 * @param br
	 * @param attr
	 * @return redirect:novelwebManager
	 */
	@RequestMapping(value = "classupdate", method = RequestMethod.POST)
	public String updateClass(@Validated(value = {ValidUpdate.class})@ModelAttribute("Classification") Classification classification,BindingResult br,RedirectAttributes attr) {

		String errUpdateMsg="";
		if(!br.hasErrors()) {
			try {
				novelService.updateClassification(classification);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
				System.out.println("錯誤訊息: "+e.getMessage());
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errUpdateMsg += fieldError.getDefaultMessage()+"<br>";
			}
			attr.addFlashAttribute("errMsg", errUpdateMsg);
		}
					
			
			return "redirect:novelwebManager";
	}	
	
	
	
	
	/*=============================*/
	/*     幻燈片管理頁面相關方法                  */
	/*=============================*/	
	/**
	 * 顯示幻燈片管理頁面
	 * @param model
	 * @return slideshowManager.jsp
	 */
	@RequestMapping(value = "slideshowManager", method = RequestMethod.GET)
	public String slideshowList(Model model,HttpSession session, RedirectAttributes attr) {
		if(session.getAttribute("admin")==null) {
			return "redirect:login";
		}
		try {
			List<Slideshow> slideshowList = adminService.findAllSlideshowList();
			model.addAttribute("slideshowlist", slideshowList);
		} catch (Exception e) {
			System.out.println("錯誤訊息: "+e.getMessage());
		}
		
		return "adminpage/slideshowManager";
	}
	
	/**
	 * 新增 幻燈片
	 * @param slideshow
	 * @param br
	 * @param imgfile
	 * @param request
	 * @param attr
	 * @return redirect:slideshowManager
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value ="addSlideshowManager", method = RequestMethod.POST)
	public String addSlideshow(@Validated(value = {ValidAdd.class})@ModelAttribute("Slideshow") Slideshow slideshow,BindingResult br, MultipartFile imgfile,HttpServletRequest request,RedirectAttributes attr) throws IllegalStateException, IOException {
		
		String errMsg="";
		if(!br.hasErrors()) {
			String imgPath=new FileUploadPath().getImgLoadPath(slideshow, imgfile, request);
			try {
				adminService.addSlideshow(slideshow,imgPath);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
				System.out.println("錯誤訊息: "+e.getMessage());
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
			attr.addFlashAttribute("errMsg", errMsg);
		}
		return "redirect:slideshowManager";
	}	
	
	
	/**
	 * 查詢幻燈片
	 * @param slideshow
	 * @param attr
	 * @return redirect:slideshowManager
	 */
	@RequestMapping(value = "slideshowfind", method = RequestMethod.POST)
	public String findSlideshow(@ModelAttribute("Slideshow") Slideshow slideshow,RedirectAttributes attr) {

			Slideshow q_slideshow;
			try {
				q_slideshow = adminService.findSlideshowById(slideshow.getId());
				attr.addFlashAttribute("q_slideshow", q_slideshow);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
			}
					
			
			return "redirect:slideshowManager";
	}	
	
	
	/**
	 * 修改 幻燈片
	 * @param slideshow
	 * @param br
	 * @param imgfile
	 * @param attr
	 * @param request
	 * @return redirect:slideshowManager
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value ="updateSlideshowManager", method = RequestMethod.POST)
	public String updateSlideshow(@Validated(value = {ValidUpdate.class})@ModelAttribute("Slideshow") Slideshow slideshow,BindingResult br, MultipartFile imgfile,RedirectAttributes attr,HttpServletRequest request) throws IllegalStateException, IOException {
		
		String errMsg="";
		if(!br.hasErrors()) {
			String imgPath=new FileUploadPath().getImgLoadPath(slideshow, imgfile, request);
			try {
				adminService.updateSlideshow(slideshow,imgPath);
			} catch (DaoException e) {
				attr.addFlashAttribute("errMsg",e.getMessage());
				System.out.println("錯誤訊息: "+e.getMessage());
			}
		} else {
			//顯示錯誤訊息
			for (FieldError fieldError : br.getFieldErrors()) {
                System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                errMsg += fieldError.getDefaultMessage()+"<br>";
			}
			attr.addFlashAttribute("errMsg", errMsg);
		}
		
		return "redirect:slideshowManager";
	}
	
	
}
