package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class GenericDAOImpl<E> implements GenericDAO<E> {

	@Inject protected SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public E save(E entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	public E update(E entity) {
		sessionFactory.getCurrentSession().update(entity);
		return entity;
	}

	public void delete(E entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

}
