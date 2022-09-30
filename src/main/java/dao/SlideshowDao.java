package dao;

import java.util.List;


import model.Slideshow;

public class SlideshowDao  implements IHibernateDao<Slideshow> {



	@Override
	public void add(Slideshow o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Slideshow queryId(int id) {
		String HQL = "from Slideshow where slideshow_id=?1";
		String[] params = { id + "" };
		List<Slideshow> l = HibernateDaoImpl.myQuery(HQL, params, Slideshow.class);
		return l.get(0);
	}

	@Override
	public void update(Slideshow o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Slideshow o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 查詢ID或名稱是否存在:
	 * @param id-幻燈片ID
	 * @param name-幻燈片名稱
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckIsExist(Integer id, String name) {
		String HQL = "from Slideshow where slideshow_id=?1 or name=?2";
		String[] params = { String.valueOf(id), name };
		List<Slideshow> l = HibernateDaoImpl.myQuery(HQL, params, Slideshow.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢ID是否存在:
	 * @param id-幻燈片ID
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckIsExist(Integer id) {
		String HQL = "from Slideshow where slideshow_id=?1";
		String[] params = { String.valueOf(id) };
		List<Slideshow> l = HibernateDaoImpl.myQuery(HQL, params, Slideshow.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 查詢全部幻燈片List
	 * @return List<Slideshow>
	 */
	public List<Slideshow> queryAll() {
		String HQL = "from Slideshow";
	
		List<Slideshow> l = HibernateDaoImpl.myQuery(HQL, Slideshow.class);
		return l;
	}
	
	/**
	 * 查詢全部已啟用的幻燈片List
	 * @return List<Slideshow>
	 */
	public List<Slideshow> queryAllEnable() {
		String HQL = "from Slideshow where disable=true order by index_num ASC";
		
		List<Slideshow> l = HibernateDaoImpl.myQuery(HQL, Slideshow.class);
		return l;
	}
	
	

}
