package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateDaoImpl {

	// 連線
	public static Session get() {
		Configuration con = new Configuration().configure();
		SessionFactory sf = con.buildSessionFactory();
		Session se = sf.openSession();

		return se;
	}

	// query func(String hql)
	public static <T> List<T> myQuery(String hql, Class<T> arg0) {
		List<T> list = new ArrayList<>();
		Session se = HibernateDaoImpl.get();
		try {
			Query<T> q = se.createQuery(hql, arg0);
			list = q.getResultList();
		} catch (Exception e) {
			System.out.println("Hibernate" + e.getMessage());
		} finally {
			se.close();
		}
		return list;

	}

	public static <T> List<T> myQuery(String hql, int offset, int limit, Class<T> arg0) {
		List<T> list = new ArrayList<>();
		Session se = HibernateDaoImpl.get();
		try {
			Query<T> q = se.createQuery(hql, arg0);
			q.setFirstResult(offset);// 開始位置
			q.setMaxResults(limit);// 總個幾個
			list = q.getResultList();
		} catch (Exception e) {
			System.out.println("Hibernate" + e.getMessage());
		} finally {
			se.close();
		}
		return list;
	}

	
	public static <T> List<T> myQuery(String hql, String[] parameters, int offset, int limit, Class<T> arg0) {
		List<T> list = new ArrayList<>();
		Session se = HibernateDaoImpl.get();
		try {
			Query<T> q = se.createQuery(hql, arg0);
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					q.setParameter(i + 1, parameters[i]);
				}
			}
			q.setFirstResult(offset);// 開始位置
			q.setMaxResults(limit);// 總個幾個
			list = q.getResultList();
		} catch (Exception e) {
			System.out.println("Hibernate" + e.getMessage());
		}finally {
			se.close();
		}
		
		return list;
	}

	public static <T> List<T> myQuery(String hql, String[] parameters, Class<T> arg0) {
		List<T> list = new ArrayList<>();
		Session se = HibernateDaoImpl.get();
		try {
			Query<T> q = se.createQuery(hql, arg0);
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					q.setParameter(i + 1, parameters[i]);
				}
			}
			list = q.getResultList();
		} catch (Exception e) {
			System.out.println("Hibernate" + e.getMessage());
		}finally {
			se.close();
		}

		return list;
	}

	// insert/update/delete func(Object o,String m)
	public static void myUpdate(Object o, DaoStateEnum state) {
		Session se = HibernateDaoImpl.get();

		Transaction t = se.beginTransaction();
		try {
			if ("ADD".equals(state.name())) {
				se.save(o);
			} else if ("UPDATE".equals(state.name())) {
				System.out.println("Hibernate UPDATE");
				se.update(o);
			} else if ("DELETE".equals(state.name())) {
				se.delete(o);
			}
			t.commit();

		} catch (Exception e) {
			if (t != null) {
				System.out.println("rollback");
				t.rollback();
			}
			System.out.println("Hibernate Error:" + e.getMessage());
		} finally {
			se.close();
		}
	}

}
