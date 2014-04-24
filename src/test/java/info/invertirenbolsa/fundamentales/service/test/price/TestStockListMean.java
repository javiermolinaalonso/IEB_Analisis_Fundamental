package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import info.invertirenbolsa.fundamentales.price.exceptions.StockListMeanParameterException;
import info.invertirenbolsa.fundamentales.price.impl.StockList;
import info.invertirenbolsa.fundamentales.price.impl.StockPrice;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class TestStockListMean {

    private final String TICKER = "TEST";
    
    private StockList list;
    
    @Before
    public void setUp() throws Exception {
        list = new StockList(TICKER);
    }

    @Test
    public void testGetMeanWithSameNumber() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1002), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1003), new BigDecimal(10)));
        
        StockList meanList = list.getMean(2);
        assertTrue(meanList.stream().allMatch(x -> x.getValue().intValue() == 10));
        assertEquals(3, meanList.size());
    }

    @Test
    public void testGetMeanDifferentNumbers() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(20)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1002), new BigDecimal(30)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1003), new BigDecimal(40)));
        
        StockList meanList = list.getMean(2);
        assertEquals(15, meanList.get(0).getValue().intValue());
        assertEquals(Instant.ofEpochSecond(1001).toEpochMilli(), meanList.get(0).getInstant().toEpochMilli());
        assertEquals(25, meanList.get(1).getValue().intValue());
        assertEquals(Instant.ofEpochSecond(1002).toEpochMilli(), meanList.get(1).getInstant().toEpochMilli());
        assertEquals(35, meanList.get(2).getValue().intValue());
        assertEquals(Instant.ofEpochSecond(1003).toEpochMilli(), meanList.get(2).getInstant().toEpochMilli());
    }
    
    @Test(expected=StockListMeanParameterException.class)
    public void testInvalidParameter() {
        StockList meanList = list.getMean(0);
        assertEquals(0, meanList.size());
    }
    
    @Test
    public void testMeanGreaterThanSizeList() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(20)));
        StockList meanList = list.getMean(3);
        assertTrue(meanList.isEmpty());
    }
    
    @Test
    public void testEmptyList() {
        StockList meanList = list.getMean(5);
        assertEquals(0, meanList.size());
    }
    
}
