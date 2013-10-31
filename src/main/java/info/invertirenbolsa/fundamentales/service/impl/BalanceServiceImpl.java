package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.dao.ValueDAO;
import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.exceptions.ExceptionsEnum;
import info.invertirenbolsa.fundamentales.exceptions.FundamentalsException;
import info.invertirenbolsa.fundamentales.service.BalanceService;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired private CompanyService companyService;

	@Autowired private BalanceDAO balanceDAO;
	@Autowired private ValueKeyDAO valueKeyDAO;
	@Autowired private ValueDAO valueDAO;
	
	
	@Override
	public boolean updateBalance(Balance balance, Value value) {
		checkNotNullValues(balance.getCompany(), balance, value.getKey(), value);
		companyService.createCompany(balance.getCompany());
		try{
			balanceDAO.save(balance);
		}catch(Exception e){
			e.printStackTrace();
		}
		valueKeyDAO.save(value.getKey());
		valueDAO.save(value);
		return false;
	}


	private void checkNotNullValues(Company company, Balance balance,
			ValueKey key, Value value) {
		if(value == null){
			throw new FundamentalsException(ExceptionsEnum.VALUE_NULL);
		}
		if(balance == null){
			throw new FundamentalsException(ExceptionsEnum.BALANCE_NULL);
		}
		if(key == null){
			throw new FundamentalsException(ExceptionsEnum.VALUEKEY_NULL);
		}
		if(company == null){
			throw new FundamentalsException(ExceptionsEnum.COMPANY_NULL);
		}
	}

}
