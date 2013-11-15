package info.invertirenbolsa.fundamentales.dao;

import info.invertirenbolsa.fundamentales.domain.Company;

public interface CompanyDAO extends GenericDAO<Company> {

	public Company getCompany(Integer id);
	
	public Company getCompanyByTicker(String ticker);
	
	public Company load(Company company);

	Company getCompanyByCif(String cif);
	
}
