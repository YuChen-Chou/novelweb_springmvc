package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AdminDao;
import dao.SlideshowDao;
import exception.DaoException;
import model.Admin;
import model.Slideshow;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private SlideshowDao slideshowDao;
	

	@Override
	public Admin findById(int id) {
		if(adminDao.ckeckIdIsExist(id))
			return adminDao.queryId(id);
		throw new DaoException("ID不存在");
	}

	@Override
	public void addAdmin(Admin admin) {
		if(!ckeckUsernameIsExist(admin.getUsername())) {
			if(!ckeckEmailIsExist(admin.getEmail())) {
				adminDao.add(admin);
			} else {
				throw new DaoException("信箱已經被使用過了");
			}
		} else {
			throw new DaoException("帳號已經被使用過了");
		}
	}

	@Override
	public void updateAdmin(Admin admin) {
		if(ckeckUsernameIsExist(admin.getUsername())) {
			Admin q_admin = adminDao.queryAdmin(admin.getUsername());
			String email = q_admin.getEmail();
			
			if(!ckeckEmailIsExist(admin.getEmail()) || email.equals(admin.getEmail())) {
				System.out.println("=======更新資料=======");
				adminDao.update(admin);
			} else {
				throw new DaoException("信箱已經被使用過了");
			}
		} else {
			throw new DaoException("該帳號不存在");
		}
		
	}

	@Override
	public void deleteAdmin(Admin admin) {
		if(ckeckUsernameIsExist(admin.getUsername())) {
			adminDao.delete(admin);
		}else {
			throw new DaoException("該帳號不存在");
		}
	}

	@Override
	public List<Admin> findAllAdmin() {
		return adminDao.queryAll();
	}

	@Override
	public Admin queryAdmin(String username) {
		if(ckeckUsernameIsExist(username)) {
			Admin admin = adminDao.queryAdmin(username);
			return admin;
		} 
		throw new DaoException("該帳號不存在");
	}

	@Override
	public boolean ckeckEmailIsExist(String email) {
		if(adminDao.ckeckEmailIsExist(email))
			return true;
		return false;
	}

	@Override
	public boolean ckeckUsernameIsExist(String username) {
		if(adminDao.ckeckUsernameIsExist(username))
			return true;
		return false;
	}

	@Override
	public Admin login(String username, String password) {
		if(adminDao.ckeckloginIsExist(username, password)) {
			Admin admin = adminDao.queryAdmin(username, password);
			return admin;
		}
		throw new DaoException("(帳號密碼錯誤，請重新輸入)");
	}


	//=======================//
	//       幻燈片相關方法              //
	//=======================//

	@Override
	public void addSlideshow(Slideshow slideshow,String imgPath) {
		String name=slideshow.getName().trim();
		if(name!="")
			if(!slideshowDao.ckeckIsExist(slideshow.getId(), name)) {
				if(!imgPath.isEmpty()) {
					slideshow.setPicture(imgPath);
					slideshowDao.add(slideshow);
				}else {
					throw new DaoException("未放入圖片");
				}
			}
			else {
				throw new DaoException("不可以重複新增");
			}
		else {
			throw new DaoException("不可以是空白");
		}
		
	}

	@Override
	public List<Slideshow> findAllSlideshowList() {
		return slideshowDao.queryAll();
	}

	@Override
	public Slideshow findSlideshowById(Integer id) {
		if(slideshowDao.ckeckIsExist(id))
			return slideshowDao.queryId(id);
		throw new DaoException("查詢的ID不存在");
	}

	@Override
	public void updateSlideshow(Slideshow slideshow, String imgPath) {
		Slideshow q_slideshow = slideshowDao.queryId(slideshow.getId());
		if(!imgPath.isEmpty()) {
			slideshow.setPicture(imgPath);//新圖片
		}else {
			slideshow.setPicture(q_slideshow.getPicture());//保持原來圖片
		}
		slideshowDao.update(slideshow);
	}



}
