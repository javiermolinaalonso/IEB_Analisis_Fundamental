package info.invertirenbolsa.fundamentales.dao.impl;

import java.util.List;

import info.invertirenbolsa.fundamentales.dao.ValueDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Value;

import org.springframework.stereotype.Repository;

@Repository("ValueDAO")
public class ValueDAOImpl extends GenericDAOImpl implements ValueDAO {

	@Override
	public List<Value> getAll() {
		throw new UnsupportedOperationException();
	}
}
