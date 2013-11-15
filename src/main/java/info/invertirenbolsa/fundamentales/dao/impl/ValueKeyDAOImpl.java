package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.exceptions.ExceptionsEnum;
import info.invertirenbolsa.fundamentales.exceptions.FundamentalsException;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("ValueKeyDAO")
public class ValueKeyDAOImpl extends GenericDAOImpl<ValueKey> implements ValueKeyDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ValueKey> getAll() {
		return getSession().createCriteria(ValueKey.class).list();
	}

	@Override
	public Boolean exists(ValueKey entity){
		return load(entity) != null;
	}
	
	@Override
	public ValueKey load(ValueKey entity) {
		ValueKey storedKey = null;
		if(entity.getId() != null){
			storedKey = (ValueKey) sessionFactory.getCurrentSession().load(entity.getClass(), entity.getId());
		}else if(entity.getXbrlid() != null){
			Criteria cr = getSession().createCriteria(ValueKey.class);
			cr.add(Restrictions.eq("xbrlid", entity.getXbrlid()));
			storedKey = (ValueKey) cr.uniqueResult();
		}
		if(storedKey != null){
			return storedKey;
		}
		throw new FundamentalsException(ExceptionsEnum.VALUEKEY_NOT_FOUND);
	}
}
