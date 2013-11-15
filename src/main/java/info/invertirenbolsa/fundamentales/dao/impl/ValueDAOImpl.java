package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.ValueDAO;
import info.invertirenbolsa.fundamentales.domain.Value;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;

@Repository("ValueDAO")
public class ValueDAOImpl extends GenericDAOImpl<Value> implements ValueDAO {

	@Override
	public List<Value> getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value load(Value entity) {
		throw new NotYetImplementedException("This is a todo");
	}
}
