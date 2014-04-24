package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;

import info.invertirenbolsa.fundamentales.price.impl.StockList;
import info.invertirenbolsa.fundamentales.price.impl.StockPrice;

import org.junit.Before;
import org.junit.Test;

public class TestStockListFirstDerivate {

private final String TICKER = "TEST";
    
    private StockList list;
    
    @Before
    public void setUp() throws Exception {
        list = new StockList(TICKER);
    }
    
    @Test
    public void testGetFirstDerivatePlain() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(10)));
        assertEquals(0, list.getFirstDerivate().get(0).getValue().intValue());
    }
    
    @Test
    public void testGetFirstDerivateSimple() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(20)));
        assertEquals(10, list.getFirstDerivate().get(0).getValue().intValue());
    }
    
    @Test
    public void testGetFirstDerivateNegative() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(20)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(10)));
        assertEquals(-10, list.getFirstDerivate().get(0).getValue().intValue());
    }
    
    @Test
    public void testGetFirstDerivate() {
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1000), new BigDecimal(10)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(15)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(20)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(0)));
        list.add(new StockPrice(TICKER, Instant.ofEpochSecond(1001), new BigDecimal(4)));
        StockList derivateList = list.getFirstDerivate();
        assertEquals(5, derivateList.get(0).getValue().intValue());
        assertEquals(5, derivateList.get(1).getValue().intValue());
        assertEquals(-20, derivateList.get(2).getValue().intValue());
        assertEquals(4, derivateList.get(3).getValue().intValue());
    }

}
