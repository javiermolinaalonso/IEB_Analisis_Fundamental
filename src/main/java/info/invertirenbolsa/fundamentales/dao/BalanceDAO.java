package info.invertirenbolsa.fundamentales.dao;

import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;

public interface BalanceDAO extends GenericDAO {

	public Balance loadBalance(Company company, String period);
	
}
