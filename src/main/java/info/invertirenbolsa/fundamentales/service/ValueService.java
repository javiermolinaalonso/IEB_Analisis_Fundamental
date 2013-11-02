package info.invertirenbolsa.fundamentales.service;

import info.invertirenbolsa.fundamentales.domain.Value;

public interface ValueService {

	/**
	 * This is the core method to create or modify a value of a balance
	 * If the company, balance and/or key does not exists in the database it will be created
	 * @param value The value you want to store with complete information about company, balance and valuekey
	 * @return true if everything was ok, false otherwise
	 */
	public boolean insertValue(Value value);
}
