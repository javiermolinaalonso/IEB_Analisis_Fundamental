package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("BalanceDAO")
public class BalanceDAOImpl extends GenericDAOImpl<Balance> implements BalanceDAO {

	@Override
	public Balance loadBalance(Company company, String period) {
		Criteria cr = getSession().createCriteria(Balance.class);
		cr.add(Restrictions.eq("company", company));
		cr.add(Restrictions.eq("period", period));
		return (Balance) cr.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Balance> getAll() {
		return getSession().createCriteria(Balance.class).list();
	}

	@Override
	public Boolean exists(Balance balance) {
		Balance b = loadBalance(balance.getCompany(), balance.getPeriod());
		if(b == null){
			return super.exists(balance);
		}else{
			return true;
		}
	}

	@Override
	public Balance load(Balance entity) {
		return loadBalance(entity.getCompany(), entity.getPeriod());
	}

}
