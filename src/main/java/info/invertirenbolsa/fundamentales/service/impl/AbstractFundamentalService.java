package info.invertirenbolsa.fundamentales.service.impl;

import info.invertirenbolsa.fundamentales.dao.GenericDAO;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class AbstractFundamentalService<E> {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	public E saveOrIgnore(E entity){
		try{
			logger.debug("Saving " + entity.toString());
			E savedCompany = (E) getGenericDAO().save(entity);
			logger.debug("Entity "+ entity.toString()+" saved");
			return savedCompany;
		}catch(DataIntegrityViolationException dive){
			logger.info("The entity " + entity.toString() +" already exists");
			return entity;
		}catch(Exception e){
			//TODO Parametrize this exception
			e.printStackTrace();
			return entity;
		}
	}
	
	public abstract GenericDAO<E> getGenericDAO();
}
