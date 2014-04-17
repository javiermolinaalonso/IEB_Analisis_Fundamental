package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.assertEquals;
import info.invertirenbolsa.fundamentales.price.StockService;
import info.invertirenbolsa.fundamentales.price.exceptions.EmptyStatisticListException;
import info.invertirenbolsa.fundamentales.price.impl.StockList;
import info.invertirenbolsa.fundamentales.price.impl.StockPrice;
import info.invertirenbolsa.fundamentales.price.impl.StockServiceImpl;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class TestPriceService {

    private static final String INTC = "INTC";
    private static final String V = "V";
    
    StockService stockService;
    StockList s1;
    StockList s2;
    
    @Before
    public void setUp() throws Exception {
        stockService = new StockServiceImpl();
        s1 = new StockList(INTC);
        s1.add(new StockPrice(INTC, Instant.ofEpochSecond(1), new BigDecimal(10)));
        s1.add(new StockPrice(INTC, Instant.ofEpochSecond(2), new BigDecimal(11)));
        s1.add(new StockPrice(INTC, Instant.ofEpochSecond(3), new BigDecimal(12)));
        s1.add(new StockPrice(INTC, Instant.ofEpochSecond(4), new BigDecimal(11)));
        s1.add(new StockPrice(INTC, Instant.ofEpochSecond(5), new BigDecimal(12)));
    }

    @Test
    public void testGetCovarianceSimple() {
        s2 = new StockList(V);
        s2.add(new StockPrice(V, Instant.ofEpochSecond(1), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(2), new BigDecimal(5)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(3), new BigDecimal(2)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(4), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(5), new BigDecimal(1)));
        assertEquals(-0.56d, stockService.computeCorrelation(s1, s2).getCorrelation().doubleValue(), 0.01d);
    }
    
    @Test
    public void testGetCovarianceWithMoreElementsAtSecondList() {
        s2 = new StockList(V);
        s2.add(new StockPrice(V, Instant.ofEpochSecond(1), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(2), new BigDecimal(5)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(3), new BigDecimal(2)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(4), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(5), new BigDecimal(1)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(6), new BigDecimal(1)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(7), new BigDecimal(1)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(8), new BigDecimal(1)));
        assertEquals(-0.56d, stockService.computeCorrelation(s1, s2).getCorrelation().doubleValue(), 0.01d);
    }
    
    @Test
    public void testGetCovarianceWithMoreElementsAtFirstList() {
        s2 = new StockList(V);
        s2.add(new StockPrice(V, Instant.ofEpochSecond(1), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(2), new BigDecimal(5)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(3), new BigDecimal(2)));
        assertEquals(-0.33d, stockService.computeCorrelation(s1, s2).getCorrelation().doubleValue(), 0.01d);
    }
    
    @Test
    public void testGetCovarianceWithDifferentElements() {
        s2 = new StockList(V);
        s2.add(new StockPrice(V, Instant.ofEpochSecond(1), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(2), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(5), new BigDecimal(12)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(12), new BigDecimal(3)));
        s2.add(new StockPrice(V, Instant.ofEpochSecond(15), new BigDecimal(1)));
        assertEquals(3d, stockService.computeCorrelation(s1, s2).getCorrelation().doubleValue(), 0.01d);
    }

    @Test(expected=EmptyStatisticListException.class)
    public void testGetCovarianceNoList() {
        s2 = new StockList(V);
        stockService.computeCorrelation(s1, s2);
    }
}
