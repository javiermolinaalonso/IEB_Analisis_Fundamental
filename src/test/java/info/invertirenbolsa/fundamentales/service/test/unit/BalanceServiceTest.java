package info.invertirenbolsa.fundamentales.service.test.unit;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.domain.enums.BalanceType;
import info.invertirenbolsa.fundamentales.service.ValueService;
import info.invertirenbolsa.fundamentales.utils.HSQLServerUtil;

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
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "file:src/test/resources/application-context.xml" })
public class BalanceServiceTest extends DBTestCase {

	@Inject private ValueService valueService;
	@Inject private CompanyDAO companyDAO;
	
	public BalanceServiceTest(){
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/fundamentals");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "postgres");  
	}
	
	@Before  
    public void setUp() throws Exception {  
        HSQLServerUtil.getInstance().start("fundamentals");  
        super.setUp();  
    }
	
	@Test
	public void testEmptyDatabase(){
		assertNull(companyDAO.getCompanyByTicker("TEF.MC"));
		Company c = new Company("Telefonica", "A00000000", "TEF.MC");
		Balance b = new Balance(c, "2012-Q2", BalanceType.BA);
		ValueKey key = new ValueKey(1100, "Total activo");
		Value value = new Value(b, key, 1330d);
		valueService.insertValue(value);
		
		assertEquals(Integer.valueOf(1), c.getId());
		assertEquals(Integer.valueOf(1), b.getId());
		assertEquals(Integer.valueOf(1), key.getId());
		assertEquals(Integer.valueOf(1), value.getId());
		
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("./src/test/resources/datasets/EmptyDatabase.xml"));
	}

}
