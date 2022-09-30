package dao;

import java.util.List;

import model.Classification;

public class ClassificationDao implements IHibernateDao<Classification> {


	@Override
	public void add(Classification o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Classification queryId(int id) {
		String HQL= "from Classification where classification_id=?1";
		String[] params = {id+""};
		List<Classification> l=HibernateDaoImpl.myQuery(HQL,params,Classification.class);
		return l.get(0);
	}

	@Override
	public void update(Classification o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Classification o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}
	
	/**
	 * 查詢全部小說分類list
	 * @return List<Classification>
	 */
	public List<Classification> queryAll() {
		String HQL = "from Classification";
		List<Classification> l = HibernateDaoImpl.myQuery(HQL, Classification.class);
		return l;
	}
	
	/**
	 * 檢查該分類是否被創建過
	 * @param id-分類ID
	 * @param name-分類名稱
	 * @return true存在,false:不存在
	 */
	public boolean ckeckIsExist(int id,String name) {
		String HQL = "from Classification where classification_id=?1 or name=?2";
		String[] params = { String.valueOf(id), name };
		List<Classification> l = HibernateDaoImpl.myQuery(HQL, params, Classification.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	//
	/**
	 * 檢查id是否存在:
	 * @param id-分類ID
	 * @return true存在,false:不存在
	 */
	public boolean ckeckIsIDExist(int id) {
		String HQL = "from Classification where classification_id=?1";
		String[] params = { String.valueOf(id) };
		List<Classification> l = HibernateDaoImpl.myQuery(HQL, params, Classification.class);
		if (l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 檢查該分類名稱是否可用:
	 * @param ClassificationName-分類名稱
	 * @return true可用，false不可用
	 */
	public boolean ckeckClassificationNameIsExist(String ClassificationName) {
		String HQL = "from Classification where name=?1";
		String[] params = { ClassificationName };
		List<Classification> l = HibernateDaoImpl.myQuery(HQL, params, Classification.class);
		if (l.size() > 0)
			return true;
		return false;
	}
}
