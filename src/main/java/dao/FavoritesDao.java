package dao;

import java.util.List;

import model.Favorites;

public class FavoritesDao implements IHibernateDao<Favorites> {


	@Override
	public void add(Favorites o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Favorites queryId(int id) {
		String HQL = "from Favorites where favorites_id=?1";
		String[] params = { id + "" };
		List<Favorites> l = HibernateDaoImpl.myQuery(HQL, params, Favorites.class);
		return l.get(0);
	}

	@Override
	public void update(Favorites o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Favorites o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	
	/**
	 * 透過會員ID查詢全部加入喜好的小說
	 * @param memberId-會員ID
	 * @return List<Favorites>
	 */
	public List<Favorites> queryAllByMemberId(int memberId) {
		String HQL = "from Favorites where member_id=?1";
		String[] params = { memberId + "" };
		List<Favorites> l = HibernateDaoImpl.myQuery(HQL, params, Favorites.class);
		return l;
	}
	
	/**
	 * 查詢該小說id是否已經被該登入會員收藏
	 * @param memberId-會員ID
	 * @param novelId-小說ID
	 * @return true:存在，false:不存在
	 */
	public boolean ckeckNovelIdIsExist(int memberId, int novelId) {
		String HQL = "from Favorites where member_id=?1 and novel_Id=?2";
		String[] params = { memberId+"",  novelId+""};
		List<Favorites> l = HibernateDaoImpl.myQuery(HQL, params, Favorites.class);
		if (l.size() > 0)
			return true;
		return false;
	}

}
