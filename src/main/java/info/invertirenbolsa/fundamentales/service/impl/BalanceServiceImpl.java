package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.dao.GenericDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.service.BalanceService;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl extends AbstractFundamentalService<Balance> implements BalanceService {

	@Autowired private CompanyService companyService;
	
	@Autowired private BalanceDAO balanceDAO;
	
	
	@Override
	public Balance createBalance(Balance balance) {
		companyService.createCompany(balance.getCompany());
		return super.saveOrIgnore(balance);
	}


	@Override
	public GenericDAO<Balance> getGenericDAO() {
		return balanceDAO;
	}


	@Override
	public List<Balance> getBalances(String cifOrTicker, String... periods) {
		Company c = companyService.loadCompany(cifOrTicker);
		List<Balance> balances = new ArrayList<Balance>();
		for(String period : periods){
			Balance b = balanceDAO.loadBalance(c, period);
			balances.add(b);
		}
		return balances;
	}
	

}
