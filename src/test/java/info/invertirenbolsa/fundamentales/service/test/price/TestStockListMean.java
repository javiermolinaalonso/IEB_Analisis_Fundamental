package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;

import info.invertirenbolsa.fundamentales.price.impl.StockList;
import info.invertirenbolsa.fundamentales.price.impl.StockPrice;

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
    public void testGetMean() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1002), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1003), new BigDecimal(10)));
        
        StockList meanList = list.getMean(2);
        assertTrue(meanList.stream().allMatch(x -> x.getValue().intValue() == 10));
        assertEquals(3, meanList.size());
    }

}
