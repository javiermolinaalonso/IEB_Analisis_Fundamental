package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.dao.GenericDAO;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends AbstractFundamentalService<Company> implements CompanyService {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired private CompanyDAO companyDao;
	
	@Override
	public Company createCompany(String cif, String name, String ticker) {
		return createCompany(new Company(name, cif, ticker));
	}

	@Override
	public Company createCompany(Company company) {
		return super.saveOrIgnore(company);
	}

	public Company loadCompany(Integer companyId){
		return companyDao.getCompany(companyId);
	}

	@Override
	public Company loadCompany(String cifOrTicker) {
		Company c = companyDao.getCompanyByTicker(cifOrTicker);
		if(c == null){
			c = companyDao.getCompanyByCif(cifOrTicker);
		}
		return c;
	}

	@Override
	public GenericDAO<Company> getGenericDAO() {
		return companyDao;
	}
}