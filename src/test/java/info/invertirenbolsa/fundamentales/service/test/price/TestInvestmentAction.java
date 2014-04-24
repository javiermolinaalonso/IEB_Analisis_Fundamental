package info.invertirenbolsa.fundamentales.service.test.price;

import static org.junit.Assert.assertEquals;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentAction;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentActionEnum;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class TestInvestmentAction {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAmountInvestedBuy() {
        InvestmentAction action = new InvestmentAction("TEST", InvestmentActionEnum.BUY, Instant.now(), new BigDecimal(20), 10);
        assertEquals(200, action.getAmountInvested().intValue());
    }

    @Test
    public void testAmountInvestedSell() {
        InvestmentAction action = new InvestmentAction("TEST", InvestmentActionEnum.SELL, Instant.now(), new BigDecimal(20), 10);
        assertEquals(-200, action.getAmountInvested().intValue());
    }
}
