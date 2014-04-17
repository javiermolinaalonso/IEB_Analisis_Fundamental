package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.assertEquals;
import info.invertirenbolsa.fundamentales.price.impl.FactoryStatisticList;
import info.invertirenbolsa.fundamentales.price.impl.StatisticList;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class TestStatisticList {

    private StatisticList prices;
    
    @Before
    public void setUp() throws Exception {
        prices = new StatisticList();
        prices.add(BigDecimal.valueOf(10d));
        prices.add(BigDecimal.valueOf(11d));
        prices.add(BigDecimal.valueOf(12d));
        prices.add(BigDecimal.valueOf(11d));
        prices.add(BigDecimal.valueOf(12d));
    }

    @Test
    public void testMaximum() {
        assertEquals(BigDecimal.valueOf(12d), prices.getHighest());
    }
    
    @Test
    public void testMinimum() {
        assertEquals(BigDecimal.valueOf(10d), prices.getLowest());
    }
    
    @Test
    public void testMean() {
        assertEquals(11.2d, prices.getMean().doubleValue(), 0.001d);
    }

    @Test
    public void testStdDev() {
        assertEquals(0.748d, prices.getStdDev().doubleValue(), 0.001d);
    }
    
    @Test
    public void testCovariance() {
        StatisticList otherList = new StatisticList();
        otherList.add(BigDecimal.valueOf(3));
        otherList.add(BigDecimal.valueOf(5));
        otherList.add(BigDecimal.valueOf(2));
        otherList.add(BigDecimal.valueOf(3));
        otherList.add(BigDecimal.valueOf(1));
        assertEquals(-0.56d, prices.getCovariance(otherList).doubleValue(), 0.001d);
    }
    
    @Test
    public void testCorrelation() {
        StatisticList otherList = new StatisticList();
        otherList.add(BigDecimal.valueOf(3));
        otherList.add(BigDecimal.valueOf(5));
        otherList.add(BigDecimal.valueOf(2));
        otherList.add(BigDecimal.valueOf(3));
        otherList.add(BigDecimal.valueOf(1));
        assertEquals(-0.564d, prices.getCorrelation(otherList).doubleValue(), 0.001d);
    }
    
    @Test
    public void testCovarianceLongValues(){
        String MAX = "1000.0";
        Integer size = 500000;
    
        StatisticList X = FactoryStatisticList.getRandomList(MAX, size);
        StatisticList Y = FactoryStatisticList.getRandomList(MAX, size);
        assertEquals(BigDecimal.ZERO.doubleValue(), X.getCovariance(Y).doubleValue(), 0.00001d);
    }
    
    @Test
    public void testCovarianceIdentityEqualsStdDev(){
        assertEquals(prices.getCovariance(prices).doubleValue(), prices.getStdDev().pow(2).doubleValue(), 0.001d);
        
    }
}
