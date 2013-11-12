package info.invertirenbolsa.fundamentales.controller;

import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/company")
public class CompanyAPIRest {

	@Autowired private CompanyService companyService;
	
	@RequestMapping(value="",  method=RequestMethod.GET, consumes="application/json", produces="application/json")
	public @ResponseBody Company getCompany(@RequestParam(value="cif", required = false) String cif,
											@RequestParam(value="ticker", required = false) String ticker){
		Company company = companyService.loadCompany(cif != null ? cif : ticker);
		return company;
	}
	
	@RequestMapping(value="",  method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody Company insertCompany(@RequestBody Company company){
		companyService.createCompany(company);
		return company;
	}
}
