package info.invertirenbolsa.fundamentales.service;

import info.invertirenbolsa.fundamentales.domain.ValueKey;

import java.util.List;

public interface ValueKeyService {

	public List<ValueKey> getAll();
	
	public ValueKey createValueKey(ValueKey valuekey);
	
	public Boolean checkValueKey(ValueKey valuekey);
	
}
