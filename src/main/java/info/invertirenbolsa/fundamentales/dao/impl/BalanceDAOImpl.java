package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("BalanceDAO")
public class BalanceDAOImpl extends GenericDAOImpl implements BalanceDAO {

	@Override
	public Balance loadBalance(Company company, String period) {
		Criteria cr = getSession().createCriteria(Balance.class);
		cr.add(Restrictions.eq("company", company));
		cr.add(Restrictions.eq("period", period));
		return (Balance) cr.uniqueResult();
	}

	@Override
	public List<Balance> getAll() {
		return getSession().createCriteria(Balance.class).list();
	}

}
