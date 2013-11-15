package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.ValueDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.exceptions.ExceptionsEnum;
import info.invertirenbolsa.fundamentales.exceptions.FundamentalsException;
import info.invertirenbolsa.fundamentales.service.BalanceService;
import info.invertirenbolsa.fundamentales.service.ValueKeyService;
import info.invertirenbolsa.fundamentales.service.ValueService;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueServiceImpl extends AbstractFundamentalService<Value> implements ValueService {

	@Autowired private BalanceService balanceService;
	@Autowired private ValueKeyService valueKeyService;
	@Autowired private ValueDAO valueDAO;
	
	@Override
	public boolean insertValue(Value value) {
		checkNotNullValues(value.getBalance().getCompany(), value.getBalance(), value.getKey(), value);
		
		value.setBalance(balanceService.createBalance(value.getBalance()));
		value.setKey(valueKeyService.createValueKey(value.getKey()));
		//TODO Check if exists?
		try{
			valueDAO.save(value);
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}
	
	private void checkNotNullValues(Company company, Balance balance, ValueKey key, Value value) {
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

	@Override
	public ValueDAO getGenericDAO() {
		return valueDAO;
	}

}
