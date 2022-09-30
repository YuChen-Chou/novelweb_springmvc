package dao;

import java.util.List;

import model.Memberclass;

public class MemberclassDao implements IHibernateDao<Memberclass> {


	@Override
	public void add(Memberclass o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Memberclass queryId(int id) {
		String HQL = "from Memberclass where class_id=?1";
		String[] params = { id + "" };
		List<Memberclass> l = HibernateDaoImpl.myQuery(HQL, params, Memberclass.class);
		return l.get(0);
	}

	@Override
	public void update(Memberclass o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Memberclass o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}

}
