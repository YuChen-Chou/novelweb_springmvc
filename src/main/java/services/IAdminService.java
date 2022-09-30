package services;

import java.util.List;

import model.Admin;
import model.Slideshow;

public interface IAdminService {
	/**
	 * 查詢id返回Admin物件
	 * @param id
	 * @return Admin物件
	 */
	Admin findById(int id);
	
	/**
	 * 新增Admin物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param admin
	 */
	void addAdmin(Admin admin);
	
	/**
	 * 更新Admin物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param admin
	 */
	void updateAdmin(Admin admin);
	
	/**
	 * 刪除Admin物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param admin
	 */
	void deleteAdmin(Admin admin);
	
	
	/**
	 * 查詢全部Admin物件
	 * @return List<Admin>
	 */
	List<Admin> findAllAdmin();
	
	/**
	 * 查詢帳號返回Admin物件
	 * @param username
	 * @return Admin
	 */
	Admin queryAdmin(String username);
	
	/**
	 * 查詢email存在
	 * @param email
	 * @return true:存在,false:不存在
	 */
	boolean ckeckEmailIsExist(String email);
	
	/**
	 * 查詢username存在
	 * @param username
	 * @return true:存在,false:不存在
	 */
	boolean ckeckUsernameIsExist(String username);
	
	/**
	 * 登入驗證邏輯，密碼MD5加密驗證(加密未實作)
	 * @param username 會員帳號
	 * @param password 會員密碼
	 * @return 返回管理員資料，若不存在，則拋出異常
	 */
	Admin login(String username, String password);
	

	/**
	 * 新增幻燈片
	 * @param slideshow
	 * @param imgPath 
	 */
	void addSlideshow(Slideshow slideshow, String imgPath);

	/**
	 * 全部幻燈片list
	 * @return List<Slideshow>
	 */
	List<Slideshow> findAllSlideshowList();
	
	/**
	 * 透過id查詢幻燈片物件
	 * @param id
	 * @return Slideshow
	 */
	Slideshow findSlideshowById(Integer id);
	
	/**
	 * 更新幻燈片物件
	 * @param slideshow
	 * @param imgPath
	 */
	void updateSlideshow(Slideshow slideshow, String imgPath);
	
	
}
