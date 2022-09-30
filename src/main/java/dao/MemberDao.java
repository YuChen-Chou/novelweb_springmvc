package dao;


import java.util.List;

import model.Member;

public class MemberDao implements IHibernateDao<Member> {



	@Override
	public void add(Member o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Member queryId(int id) {
		String HQL = "from Member where member_id=?1";
		String[] params = { id + "" };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		return l.get(0);
	}

	@Override
	public void update(Member o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Member o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 查詢帳號
	 * @param username-帳號
	 * @return Member物件
	 */
	public Member queryMember(String username) {
		String HQL = "from Member where username=?1";
		String[] params = { username };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		return l.get(0);
	}
	
	/**
	 * 查詢帳號密碼
	 * @param username-帳號
	 * @param password-密碼
	 * @return admin物件
	 */
	public Member queryMember(String username, String password) {
		String HQL = "from Member where username=?1 and password=?2";
		String[] params = { username, password };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		return l.get(0);
	}
	
	/**
	 * 查詢ID是否存在:
	 * @param id
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckIdIsExist(int id) {
		String HQL = "from Member where id=?1";
		String[] params = { String.valueOf(id) };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢帳號是否存在
	 * @param username-帳號
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckUsernameIsExist(String username) {
		String HQL = "from Member where username=?1";
		String[] params = { username };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢email是否存在:
	 * @param email-信箱
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckEmailIsExist(String email) {
		String HQL = "from Member where email=?1";
		String[] params = { email };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢帳號密碼是否存在:
	 * @param username-帳號
	 * @param password-密碼
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckloginIsExist(String username,String password) {
		String HQL = "from Member where username=?1 and password=?2";
		String[] params = { username, password };
		List<Member> l = HibernateDaoImpl.myQuery(HQL, params, Member.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢全部會員list
	 * @return List<Member>
	 */
	public List<Member> queryAll() {
		// 改為自訂的查詢方式 透過參數陣列傳遞 並在該方法myQuery內開啟&關閉session連接
		String HQL = "from Member";
		List<Member> l = HibernateDaoImpl.myQuery(HQL, Member.class);
		return l;
	}
	
	

}
