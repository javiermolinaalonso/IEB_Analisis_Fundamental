package info.invertirenbolsa.fundamentales.service.test.unit;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.domain.enums.BalanceType;
import info.invertirenbolsa.fundamentales.service.ValueService;

import java.io.FileInputStream;

import javax.inject.Inject;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/application-context.xml" })
public class BalanceAddValueToExistingBalanceTest extends DBTestCase {

	@Inject private ValueService valueService;
	@Inject private CompanyDAO companyDAO;
	
	public BalanceAddValueToExistingBalanceTest(){
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/fundamentals");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "postgres");  
	}
	
	@Before  
    public void setUp() throws Exception {  
        super.setUp();  
    }
	
	@Test
	public void test(){
		assertNull(companyDAO.getCompanyByTicker("TEF.MC"));
		Company c = new Company("Telefonica", "A00000000", "TEF.MC");
		Balance b = new Balance(c, "2012Q2", BalanceType.BA);
		ValueKey activo = new ValueKey(1100, "Total activo");
		ValueKey pasivo = new ValueKey(1200, "Total pasivo");
		valueService.insertValue(new Value(b, activo, 2d));
		valueService.insertValue(new Value(b, pasivo, 2d));
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("./src/test/resources/datasets/EmptyDatabase.xml"));
	}

}
