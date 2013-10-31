package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired private CompanyDAO companyDao;
	
	@Override
	public Company createCompany(String cif, String name, String ticker) {
		return createCompany(new Company(name, cif, ticker));
	}

	@Override
	public Company createCompany(Company company) {
		try{
			Company savedCompany = companyDao.save(company);
			return savedCompany;
		}catch(Exception e){
			//TODO Parametrize this exception
			e.printStackTrace();
			return company;
		}
	}

	public Company loadCompany(Integer companyId){
		return companyDao.getCompany(companyId);
	}
}
