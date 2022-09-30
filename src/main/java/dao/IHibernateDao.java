package dao;

public interface IHibernateDao<T> {
	/**
	 * 對資料表進行添加
	 * @param o 物件型別
	 */
	void add(T o);
	
	/**
	 * 對資料表透過id進行查詢，返回該資料表物件
	 * @param id 
	 * @return 物件型別
	 */
	T queryId(int id);
	
	/**
	 * 對資料表進行更新
	 * @param o 物件型別
	 */
	void update(T o);
	
	/**
	 * 對資料表進行刪除
	 * @param o 物件型別
	 */
	void delete(T o);
}
