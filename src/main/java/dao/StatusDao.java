package dao;

import java.util.List;

import model.Status;

public class StatusDao implements IHibernateDao<Status> {



	@Override
	public void add(Status o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.ADD);
	}

	@Override
	public Status queryId(int id) {
		String HQL = "from Status where status_id=?1";
		String[] params = { id + "" };
		List<Status> l = HibernateDaoImpl.myQuery(HQL, params, Status.class);
		return l.get(0);
	}

	@Override
	public void update(Status o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.UPDATE);
	}

	@Override
	public void delete(Status o) {
		HibernateDaoImpl.myUpdate(o, DaoStateEnum.DELETE);
	}

}
