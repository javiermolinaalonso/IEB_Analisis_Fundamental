package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;
import info.invertirenbolsa.fundamentales.domain.IdentifiableEntity;
import info.invertirenbolsa.fundamentales.exceptions.ValidationException;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDAOImpl<E extends IdentifiableEntity> implements GenericDAO<E> {

	@Inject protected SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Boolean exists(IdentifiableEntity entity){
		if(entity.getId() == null){
			return false;
		}
		return sessionFactory.getCurrentSession().load(entity.getClass(), entity.getId()) != null;
		
	}
	
	@Override
	public IdentifiableEntity save(IdentifiableEntity entity) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<IdentifiableEntity>> constraints = validator.validate(entity);
		if(constraints.size() > 0){
			throw new ValidationException(constraints.iterator().next().getMessage());
		}
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	@Override
	public IdentifiableEntity update(IdentifiableEntity entity) {
		sessionFactory.getCurrentSession().update(entity);
		return entity;
	}

	@Override
	public void delete(IdentifiableEntity entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	@Override
	public abstract List<E> getAll();

}
