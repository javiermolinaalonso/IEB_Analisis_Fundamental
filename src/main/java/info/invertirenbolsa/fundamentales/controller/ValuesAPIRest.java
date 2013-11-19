package info.invertirenbolsa.fundamentales.controller;

import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.domain.enums.BalanceType;
import info.invertirenbolsa.fundamentales.service.ValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/value")
public class ValuesAPIRest {
	
	@Autowired private ValueService valueService;
	
	@RequestMapping(value="",  method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Value test(
			@RequestParam(value="v", required=false) Integer value,
			@RequestParam(value="xbrlid", required=false) Integer xbrlid,
			@RequestParam(value="keyName", required=false) String keyName,
			@RequestParam(value="keyId", required=false) Integer keyId,
			@RequestParam(value="period", required=false) String period,
			@RequestParam(value="balanceType", required=false) String balanceType,
			@RequestParam(value="balanceId", required=false) Integer balanceId,
			@RequestParam(value="companyName", required=false) String companyName,
			@RequestParam(value="companyCif", required=false) String companyCif,
			@RequestParam(value="companyTicker", required=false) String companyTicker,
			@RequestParam(value="companyId", required=false) Integer companyId
			){
		return new Value(new Balance(new Company("Telefonica", "A00000000", "TEF.MC"), "2012-S2", BalanceType.BA), new ValueKey(1000, "Boh"), 20103d);
		
	}
	
	@RequestMapping(value="",  method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody Value insertValue(	@RequestBody Value value){
		valueService.insertValue(value);
		return value;
	}
}
