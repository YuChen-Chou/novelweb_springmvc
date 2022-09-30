package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import model.Admin;

@Repository("adminDao")
public class AdminDao implements IHibernateDao<Admin> {

	@Override
	public void add(Admin o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	
	@Override
	public Admin queryId(int id) {
		String HQL = "from Admin where admin_id=?1";// Admin資料表名為model package內的class名
		String[] params = { id + "" };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		return l.get(0);
	}
	
	@Override
	public void update(Admin o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Admin o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 查詢ID是否存在
	 * @param id - admin.id
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckIdIsExist(int id) {
		String HQL = "from Admin where admin_id=?1";
		String[] params = { String.valueOf(id) };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢帳號是否存在
	 * @param username - admin.username
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckUsernameIsExist(String username) {
		String HQL = "from Admin where username=?1";
		String[] params = { username };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢email-boolean
	 * @param email - admin.email
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckEmailIsExist(String email) {
		String HQL = "from Admin where email=?1";
		String[] params = { email };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	
	/**
	 * 查詢帳號密碼是否存在(登入檢查)
	 * @param username - admin.username
	 * @param password - admin.password
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckloginIsExist(String username,String password) {
		String HQL = "from Admin where username=?1 and password=?2";
		String[] params = { username, password };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢帳號密碼
	 * @param username - admin.username
	 * @param password - admin.password
	 * @return Admin物件
	 */
	public Admin queryAdmin(String username, String password) {
		String HQL = "from Admin where username=?1 and password=?2";
		String[] params = { username, password };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		return l.get(0);
	}

	/**
	 * 查詢帳號
	 * @param username - admin.username
	 * @return Admin物件
	 */
	public Admin queryAdmin(String username) {
		String HQL = "from Admin where username=?1";
		String[] params = { username };
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, params, Admin.class);
		return l.get(0);
	}

	/**
	 * 查詢全部Admin
	 * @return List<Admin>
	 */
	public List<Admin> queryAll() {
		String HQL = "from Admin";
		List<Admin> l = HibernateDaoImpl.myQuery(HQL, Admin.class);
		return l;
	}


}
