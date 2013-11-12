package info.invertirenbolsa.fundamentales.dao;

import info.invertirenbolsa.fundamentales.domain.IdentifiableEntity;

import java.util.List;

public interface GenericDAO<E> {

	public List<E> getAll();
	
	public Boolean exists(IdentifiableEntity entity);
	
	public IdentifiableEntity save(IdentifiableEntity entity);
	
	public IdentifiableEntity update(IdentifiableEntity entity);

	public void delete(IdentifiableEntity entity);

}
