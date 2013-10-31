package info.invertirenbolsa.fundamentales.dao;

import info.invertirenbolsa.fundamentales.domain.Company;

public interface CompanyDAO extends GenericDAO<Company>{

	public Company getCompany(Integer id);
	
	public Company getCompany(String ticker);
	
	public Company getCompany(Company company);
	
}
