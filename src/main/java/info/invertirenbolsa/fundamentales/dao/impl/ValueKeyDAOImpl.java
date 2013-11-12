package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.ValueKey;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("ValueKeyDAO")
public class ValueKeyDAOImpl extends GenericDAOImpl implements ValueKeyDAO {

	@Override
	public List<Balance> getAll() {
		return getSession().createCriteria(ValueKey.class).list();
	}
}
