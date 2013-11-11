package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;
import info.invertirenbolsa.fundamentales.domain.IdentifiableEntity;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class AbstractFundamentalService<E extends IdentifiableEntity> {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	public E saveOrIgnore(E entity){
		try{
			logger.debug("Saving " + entity.toString());
			if(getGenericDAO().exists(entity)){
				return entity;
			}
			E savedEntity = (E) getGenericDAO().save(entity);
			logger.debug("Entity "+ entity.toString()+" saved");
			return savedEntity;
		}catch(DataIntegrityViolationException dive){
			logger.info("The entity " + entity.toString() +" already exists");
			return entity;
		}
	}
	
	public abstract GenericDAO getGenericDAO();
}
