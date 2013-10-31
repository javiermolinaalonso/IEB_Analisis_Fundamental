package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class GenericDAOImpl<E> implements GenericDAO<E> {

	@Inject protected SessionFactory sFactory;
	
	protected Session getSession(){
		return sFactory.getCurrentSession();
	}
	
	public E save(E entity) {
		sFactory.getCurrentSession().save(entity);
		return entity;
	}

	public E update(E entity) {
		sFactory.getCurrentSession().update(entity);
		return entity;
	}

	public void delete(E entity) {
		sFactory.getCurrentSession().delete(entity);
	}

}
