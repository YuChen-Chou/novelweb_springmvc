package dao;

import java.util.List;

import model.Chapter;
import model.PageInfoBean;

public class ChapterDao implements IHibernateDao<Chapter> {


	@Override
	public void add(Chapter o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Chapter queryId(int id) {
		String HQL = "from Chapter where chapter_id=?1";
		String[] params = { id + "" };
		List<Chapter> l = HibernateDaoImpl.myQuery(HQL, params, Chapter.class);
		return l.get(0);
	}

	@Override
	public void update(Chapter o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Chapter o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 透過小說ID查詢全部章節
	 * @param novelId-小說ID
	 * @return List<Chapter>
	 */
	public List<Chapter> queryAllChapterByNovelId(Integer novelId) {
		String HQL = "from Chapter where novel_id=?1";
		String[] params = { novelId + "" };
		List<Chapter> l = HibernateDaoImpl.myQuery(HQL, params, Chapter.class);
		return l;
	}
	
	/**
	 * 透過小說ID查詢章節(封裝成PageInfoBean<Chapter>)
	 * @param novelid-小說ID
	 * @param pageNum-當前頁
	 * @param pageSize-每頁的資料數(這裡一次一筆)
	 * @return PageInfoBean<Chapter>物件
	 */
	public PageInfoBean<Chapter> queryChapterByNovelId(String novelid, int pageNum, int pageSize) {

		String HQL = "from Chapter where novel_id=?1";
		String[] params = { novelid };
		
		List<Chapter> list = HibernateDaoImpl.myQuery(HQL, params, (pageNum - 1) * pageSize, pageSize, Chapter.class);

		// 查询總數據
		int total = HibernateDaoImpl.myQuery(HQL, params, Chapter.class).size();

		// 封裝到PageInfoBean中
		PageInfoBean<Chapter> page = new PageInfoBean<>(pageNum, pageSize, total, list, PageInfoBean.DEFAULT_CHAPTER_NAVIGATE_PAGES);
		return page;
	}

}
