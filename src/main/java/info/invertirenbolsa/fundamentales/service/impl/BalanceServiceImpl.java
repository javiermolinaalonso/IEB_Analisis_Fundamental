package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.dao.ValueDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.service.BalanceService;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired private CompanyService companyService;
	
	@Autowired private BalanceDAO balanceDAO;
	@Autowired private ValueDAO valueDAO;
	
	
	@Override
	public Balance createBalance(Balance balance) {
		companyService.createCompany(balance.getCompany());
		try{
			Balance savedBalance = balanceDAO.save(balance);
			return savedBalance;
		}catch(Exception e){
			//TODO Parametrize this exception
			e.printStackTrace();
			return balance;
		}
	}
	

}
