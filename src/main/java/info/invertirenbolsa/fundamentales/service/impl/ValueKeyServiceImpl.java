package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.service.ValueKeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueKeyServiceImpl implements ValueKeyService {

	@Autowired private ValueKeyDAO valueKeyDAO;
	
	@Override
	public ValueKey createValueKey(ValueKey valuekey) {
		try{
			ValueKey savedValueKey = valueKeyDAO.save(valuekey);
			return savedValueKey;
		}catch(Exception e){
			//TODO Parametrize this exception
			e.printStackTrace();
			return valuekey;
		}
	}

}
