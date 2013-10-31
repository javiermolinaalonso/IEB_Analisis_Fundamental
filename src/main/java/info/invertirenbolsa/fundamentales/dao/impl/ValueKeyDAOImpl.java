package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.ValueKeyDAO;
import info.invertirenbolsa.fundamentales.domain.ValueKey;

import org.springframework.stereotype.Repository;

@Repository("ValueKeyDAO")
public class ValueKeyDAOImpl extends GenericDAOImpl<ValueKey> implements ValueKeyDAO {

}
