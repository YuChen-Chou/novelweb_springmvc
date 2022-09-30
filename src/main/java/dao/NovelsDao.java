package dao;

import java.util.List;

import model.Novels;
import model.PageInfoBean;

public class NovelsDao implements IHibernateDao<Novels> {


	@Override
	public void add(Novels o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Novels queryId(int id) {
		String HQL = "from Novels where novel_id=?1";
		String[] params = { id + "" };
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, params, Novels.class);
		return l.get(0);
	}

	@Override
	public void update(Novels o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);

	}

	@Override
	public void delete(Novels o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}

	// 
	/**
	 * 查詢全部小說list(無使用)
	 * @return List<Novels>
	 */
	public List<Novels> queryAll() {
		String HQL = "from Novels";
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, Novels.class);
		return l;
	}

	// 
	/**
	 * 透過作者ID查詢該作者全部小說list
	 * @param authorId-作者ID
	 * @return List<Novels>
	 */
	public List<Novels> queryNovelsByAuthor(int authorId) {
		String HQL = "from Novels where author_id=?1";
		String[] params = { authorId + "" };
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, params, Novels.class);
		return l;
	}

	/**
	 * 查詢ID是否存在:
	 * @param id-小說ID
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckNovelIdIsExist(int id) {
		String HQL = "from Novels where novel_id=?1";
		String[] params = { id + "" };
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, params, Novels.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 查詢該作者書名是否存在:
	 * @param authorId-作者ID
	 * @param novelName-小說名
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckNovelNameIsExist(Integer authorId, String novelName) {
		String HQL = "from Novels where author_id=?1 and name=?2";
		String[] params = { String.valueOf(authorId), novelName };
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, params, Novels.class);
		if (l.size() > 0)
			return true;
		return false;
	}

	/**
	 * 透過分類ID查詢該分類全部小說List(無使用)
	 * @param id-分類ID
	 * @return List<Novels>
	 */
	public List<Novels> queryNovelsByClassId(String id) {
		String HQL = "from Novels where classification_id=?1 Order by updatetime desc";
		String[] params = { id };
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, params, Novels.class);
		return l;
	}
	
	/**
	 * 查詢全部新->舊小說List(無使用)
	 * @return List<Novels>
	 */
	public List<Novels> queryNewAll() {
		String HQL = "from Novels Order by updatetime desc";
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, Novels.class);
		return l;
	}
	
	/**
	 * 查詢前4筆最新的小說-用於新書推薦榜
	 * @return List<Novels>
	 */
	public List<Novels> queryNewNovelsTop4() {
		String HQL = "from Novels Order by updatetime desc";
		List<Novels> l = HibernateDaoImpl.myQuery(HQL, 0, 4, Novels.class);
		return l;
	}

	/**
	 * 查詢全部最新小說(封裝到PageInfoBean<Novels>)-用於首頁按照新->舊顯示
	 * @param pageNum-當前頁
	 * @param pageSize-每頁資料數
	 * @return PageInfoBean<Novels>
	 */
	public PageInfoBean<Novels> queryNewAllByPage(int pageNum, int pageSize) {
		// 改為自訂的查詢方式 透過參數陣列傳遞 並在該方法myQuery內開啟&關閉session連接
		String HQL = "from Novels Order by updatetime desc";
		List<Novels> list = HibernateDaoImpl.myQuery(HQL, (pageNum - 1) * pageSize, pageSize, Novels.class);

		// 查询總數據
		int total = HibernateDaoImpl.myQuery(HQL, Novels.class).size();

		// 封裝到PageInfoBean中
		PageInfoBean<Novels> page = new PageInfoBean<>(pageNum, pageSize, total, list);

		return page;
	}
	
	/**
	 * 透過分類ID查詢小說(封裝到PageInfoBean<Novels>)
	 * @param classid-分類ID
	 * @param pageNum-當前頁
	 * @param pageSize-每頁資料數
	 * @return PageInfoBean<Novels>
	 */
	public PageInfoBean<Novels> queryNovelsByClassId(String classid, int pageNum, int pageSize) {
		String HQL = "from Novels where classification_id=?1 Order by updatetime desc";// Member資料表名為model
																						// package內的class名
		String[] params = { classid };
		List<Novels> list = HibernateDaoImpl.myQuery(HQL, params, (pageNum - 1) * pageSize, pageSize, Novels.class);

		// 查询總數據
		int total = HibernateDaoImpl.myQuery(HQL, params, Novels.class).size();

		// 封裝到PageInfoBean中
		PageInfoBean<Novels> page = new PageInfoBean<>(pageNum, pageSize, total, list);

		return page;
	}
	
	/**
	 * 透過搜尋關鍵字查詢小說(封裝到PageInfoBean<Novels>)
	 * @param searchString
	 * @param pageNum-當前頁
	 * @param pageSize-每頁資料數
	 * @return PageInfoBean<Novels>
	 */
	public PageInfoBean<Novels> queryNovelsBySearchString(String searchString, Integer pageNum,
			Integer pageSize) {
		String HQL = "select n"
				+ "    from Novels n, Author a"
				+ "    where n.author=a.id"
				+ "    and a.name like '%"+searchString+"%' "
				+ "    or n.author=a.id"
				+ "    and n.name like '%"+searchString+"%' ";// 在使用替換的名稱n時，n.XXX用的是欄位字段，而非author_id
		
		List<Novels> list = HibernateDaoImpl.myQuery(HQL,  (pageNum - 1) * pageSize, pageSize, Novels.class);

		// 查询總數據
		int total = HibernateDaoImpl.myQuery(HQL,  Novels.class).size();

		// 封裝到PageInfoBean中
		PageInfoBean<Novels> page = new PageInfoBean<>(pageNum, pageSize, total, list);

		return page;
	}

}
