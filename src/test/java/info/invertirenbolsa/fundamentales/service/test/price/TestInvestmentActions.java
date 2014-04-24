package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.assertEquals;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentAction;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentActionEnum;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentActions;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class TestInvestmentActions {

    private static final String TICKER = "TEST";
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAmountInvestedBuy() {
        InvestmentActions actions = new InvestmentActions();
        InvestmentAction a1 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(20), 10);
        InvestmentAction a2 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(10), 15);
        InvestmentAction a3 = new InvestmentAction(TICKER, InvestmentActionEnum.SELL, Instant.now(), new BigDecimal(50), 25);
        actions.add(a1);
        actions.add(a2);
        actions.add(a3);
        assertEquals(900, actions.getBenefit().intValue());
    }

    @Test
    public void testAmountInvestedSell() {
        InvestmentActions actions = new InvestmentActions();
        InvestmentAction a1 = new InvestmentAction(TICKER, InvestmentActionEnum.SELL, Instant.now(), new BigDecimal(20), 10);
        InvestmentAction a2 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(20), 20);
        actions.add(a1);
        actions.add(a2);
        assertEquals(-200, actions.getBenefit().intValue());
    }
 
    @Test
    public void testAmountInvested() {
        InvestmentActions actions = new InvestmentActions();
        InvestmentAction a1 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(20), 10);
        InvestmentAction a2 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(10), 15);
        InvestmentAction a3 = new InvestmentAction(TICKER, InvestmentActionEnum.SELL, Instant.now(), new BigDecimal(50), 25);
        actions.add(a1);
        actions.add(a2);
        actions.add(a3);
        assertEquals(350, actions.getAmountInvested().intValue());
    }
    
    @Test
    public void testProfitability() {
        InvestmentActions actions = new InvestmentActions();
        InvestmentAction a1 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(20), 10);
        InvestmentAction a2 = new InvestmentAction(TICKER, InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(10), 15);
        InvestmentAction a3 = new InvestmentAction(TICKER, InvestmentActionEnum.SELL, Instant.now(), new BigDecimal(50), 25);
        actions.add(a1);
        actions.add(a2);
        actions.add(a3);
        assertEquals(2.57, actions.getProfitability().doubleValue(), 0.01d);
    }
}
