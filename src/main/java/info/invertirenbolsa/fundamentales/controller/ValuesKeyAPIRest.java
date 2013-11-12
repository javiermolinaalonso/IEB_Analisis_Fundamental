package info.invertirenbolsa.fundamentales.controller;

import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.service.ValueKeyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/valuekey")
public class ValuesKeyAPIRest {

	@Autowired private ValueKeyService valueKeyService;
	
	@RequestMapping(value="/list",  method=RequestMethod.GET, consumes="application/json", produces="application/json")
	public @ResponseBody List<ValueKey> getValueKeys(){
		return valueKeyService.getAll();
	}
	
	@RequestMapping(value="",  method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody void insertValueKey(@RequestBody ValueKey[] valueKeys){
		for(ValueKey key : valueKeys){
			valueKeyService.createValueKey(key);
		}
	}
}
