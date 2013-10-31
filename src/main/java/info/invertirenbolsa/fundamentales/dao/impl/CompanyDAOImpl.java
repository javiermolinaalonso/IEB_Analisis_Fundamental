package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Company;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("companyDAO")
public class CompanyDAOImpl extends GenericDAOImpl<Company> implements CompanyDAO {

	@Override
	public Company getCompany(Company company) {
		Company idCompany = getCompany(company.getId());
		if(idCompany != null){
			return idCompany;
		}
		return getCompany(company.getTicker());
	}

	@Override
	public Company getCompany(Integer id) {
		return (Company) getSession().get(Company.class, id);
	}

	@Override
	public Company getCompany(String ticker) {
		Criteria cr = getSession().createCriteria(Company.class);
		cr.add(Restrictions.eq("ticker", ticker));
		return (Company) cr.uniqueResult();
	}

}
