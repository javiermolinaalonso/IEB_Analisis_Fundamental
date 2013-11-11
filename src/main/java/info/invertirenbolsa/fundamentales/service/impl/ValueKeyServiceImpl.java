package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;
import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.service.ValueKeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueKeyServiceImpl extends AbstractFundamentalService<ValueKey> implements ValueKeyService {

	@Autowired private ValueKeyDAO valueKeyDAO;
	
	@Override
	public ValueKey createValueKey(ValueKey valuekey) {
		return super.saveOrIgnore(valuekey);
	}

	@Override
	public GenericDAO getGenericDAO() {
		return valueKeyDAO;
	}

}
