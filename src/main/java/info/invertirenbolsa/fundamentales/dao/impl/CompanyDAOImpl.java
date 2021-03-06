package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Company;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("companyDAO")
public class CompanyDAOImpl extends GenericDAOImpl<Company> implements CompanyDAO {

	@Override
	public Company load(Company company) {
		Company idCompany = getCompany(company.getId());
		if(idCompany != null){
			return idCompany;
		}
		return getCompanyByTicker(company.getTicker());
	}

	@Override
	public Company getCompany(Integer id) {
		return (Company) getSession().get(Company.class, id);
	}

	@Override
	public Company getCompanyByTicker(String ticker) {
		Criteria cr = getSession().createCriteria(Company.class);
		cr.add(Restrictions.eq("ticker", ticker));
		return (Company) cr.uniqueResult();
	}

	@Override
	public Company getCompanyByCif(String cif) {
		Criteria cr = getSession().createCriteria(Company.class);
		cr.add(Restrictions.eq("cif", cif));
		return (Company) cr.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getAll() {
		return getSession().createCriteria(Company.class).list();
	}

	@Override
	public Boolean exists(Company company) {
		return null;
	}

}
