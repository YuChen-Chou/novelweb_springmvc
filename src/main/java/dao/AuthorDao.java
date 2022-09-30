package dao;

import java.util.List;

import model.Author;

public class AuthorDao implements IHibernateDao<Author> {

	@Override
	public void add(Author o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Author queryId(int id) {
		String HQL = "from Author where author_id=?1";
		String[] params = { id + "" };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		return l.get(0);
	}

	@Override
	public void update(Author o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Author o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 查詢作者名
	 * @param name - author.name
	 * @return Author物件
	 */
	public Author queryName(String name) {
		String HQL = "from Author where name=?1";
		String[] params = { name };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		return l.get(0);
	}
	
	
	/**
	 * 查詢作者名稱是否存在
	 * @param name - author.name
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckNameIdIsExist(String name) {
		String HQL = "from Author where name=?1";
		String[] params = { name };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢會員ID是否存在(存在:是作者會員，不存在:是一般會員)
	 * @param memberId - Author表中的會員ID欄位
	 * @return true:存在，false:不存在
	 */
	public boolean isAuthor(int memberId) {
		String HQL = "from Author where member_id=?1";
		String[] params = { memberId+"" };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢作者id是否存在
	 * @param authorId - author.id
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckAuthorIdIsExist(int authorId) {
		String HQL = "from Author where author_id=?1";
		String[] params = { authorId+"" };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	
	/**
	 * 查詢該會員id的作者物件
	 * @param memberId - Author表中的會員ID欄位
	 * @return Author物件
	 */
	public Author queryAuthor(int memberId) {
		String HQL = "from Author where member_id=?1";
		String[] params = { memberId+"" };
		List<Author> l = HibernateDaoImpl.myQuery(HQL, params, Author.class);
		return l.get(0);
	}
	

}
