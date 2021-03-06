package info.invertirenbolsa.fundamentales.service;

import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.price.Price;

import java.time.LocalDate;
import java.util.List;

public interface CompanyService {

	/**
	 * Creates a new company with the given values
	 * @param cif
	 * @param name
	 * @param ticker
	 * @return The created company
	 */
	public Company createCompany(String cif, String name, String ticker);
	
	/**
	 * Creates the specified company
	 * If the company exists it does not modify anything
	 * @param company
	 * @return The created company
	 */
	public Company createCompany(Company company);

	/**
	 * Loads the Company identified by CIF or Ticker
	 * @param cifOrTicker
	 * @return
	 */
	public Company loadCompany(String cifOrTicker);
	
	public List<Price> getPrices(LocalDate from, LocalDate to);
}
