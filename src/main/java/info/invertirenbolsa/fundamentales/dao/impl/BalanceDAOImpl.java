package info.invertirenbolsa.fundamentales.dao.impl;

import info.invertirenbolsa.fundamentales.dao.BalanceDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;

import org.springframework.stereotype.Repository;

@Repository("BalanceDAO")
public class BalanceDAOImpl extends GenericDAOImpl<Balance> implements BalanceDAO {

}
