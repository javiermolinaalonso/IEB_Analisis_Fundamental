package info.invertirenbolsa.fundamentales.controller;

import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.service.BalanceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/balance")
public class BalanceAPIRest {

	@Autowired private BalanceService balanceService;
	
	@RequestMapping(value="",  method=RequestMethod.GET, consumes="application/json", produces="application/json")
	public @ResponseBody List<Balance> getCompany(@RequestParam(value="cif", required = false) String cif,
											@RequestParam(value="ticker", required = false) String ticker,
											@RequestParam(value="period", required = true) String[] period){
		List<Balance> balances = balanceService.getBalances(cif != null ? cif : ticker, period);
		return balances;
	}
	
	@RequestMapping(value="",  method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody Balance insertCompany(@RequestBody Balance balance){
		balanceService.createBalance(balance);
		return balance;
	}
}
