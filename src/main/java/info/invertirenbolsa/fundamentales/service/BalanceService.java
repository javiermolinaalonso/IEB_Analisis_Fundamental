package info.invertirenbolsa.fundamentales.service;

import info.invertirenbolsa.fundamentales.domain.Balance;

import java.util.List;

public interface BalanceService {

	public Balance createBalance(Balance balance);
	
	public List<Balance> getBalances(String cifOrTicker, String... period);
}
