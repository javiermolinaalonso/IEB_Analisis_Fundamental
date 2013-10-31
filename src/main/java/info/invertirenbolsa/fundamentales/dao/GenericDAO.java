package info.invertirenbolsa.fundamentales.dao;

public interface GenericDAO<E> {

	public E save(E entity);
	
	public E update(E entity);

	public void delete(E entity);

}
