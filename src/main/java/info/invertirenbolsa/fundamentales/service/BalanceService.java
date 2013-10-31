package info.invertirenbolsa.fundamentales.service;

import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;

public interface BalanceService {

	/**
	 * This is the core method to create or modify a value of a balance
	 * If the company, balance and/or key does not exists in the database it will be created
	 * @param company The company
	 * @param balance An existing balance or a new one
	 * @param key The identifier of the data you want to modify
	 * @param value The value you want to store
	 * @return true if everything was ok, false otherwise
	 */
	public boolean updateBalance(Balance balance, Value value);
}
