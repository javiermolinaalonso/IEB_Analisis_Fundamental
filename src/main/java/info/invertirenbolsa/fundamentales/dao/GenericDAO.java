package info.invertirenbolsa.fundamentales.dao;

import info.invertirenbolsa.fundamentales.domain.IdentifiableEntity;

public interface GenericDAO {

	public Boolean exists(IdentifiableEntity entity);
	
	public IdentifiableEntity save(IdentifiableEntity entity);
	
	public IdentifiableEntity update(IdentifiableEntity entity);

	public void delete(IdentifiableEntity entity);

}
