package info.invertirenbolsa.fundamentales.service.test.unit;

import info.invertirenbolsa.fundamentales.dao.CompanyDAO;
import info.invertirenbolsa.fundamentales.domain.Balance;
import info.invertirenbolsa.fundamentales.domain.Company;
import info.invertirenbolsa.fundamentales.domain.Value;
import info.invertirenbolsa.fundamentales.domain.ValueKey;
import info.invertirenbolsa.fundamentales.domain.enums.BalanceType;
import info.invertirenbolsa.fundamentales.service.BalanceService;
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
public class BalanceServiceLoadBalanceTest extends DBTestCase {

	@Inject private ValueService valueService;
	@Inject private BalanceService balanceService;
	
	private String period = "2012-Q2";
	private String period2 = "2012-Q3";
	private String companyTicker = "TEF.MC";
	
	public BalanceServiceLoadBalanceTest(){
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");  
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/fundamentalstest");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "postgres");  
	}
	
	@Before  
    public void setUp() throws Exception {  
        HSQLServerUtil.getInstance().start("fundamentals");  
        super.setUp();  
        Company c = new Company("Telefonica", "A00000000", companyTicker);
		Balance b = new Balance(c, period, BalanceType.BA);
		ValueKey key = new ValueKey(1100, "Total activo");
		Value value = new Value(b, key, 1330d);
		valueService.insertValue(value);
		Balance b2 = new Balance(c, period2, BalanceType.BA);
		value = new Value(b2, key, 1240d);
		valueService.insertValue(value);
    }
	
	@Test
	public void testLoadBalance(){
		Balance b = balanceService.getBalances(companyTicker, period).get(0);
		assertEquals(BalanceType.BA, b.getBalanceType());
		assertEquals(period, b.getPeriod());
		
		Balance b2 = balanceService.getBalances(companyTicker, period2).get(0);
		assertEquals(BalanceType.BA, b2.getBalanceType());
		assertEquals(period2, b2.getPeriod());
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("./src/test/resources/datasets/EmptyDatabase.xml"));
	}

}
