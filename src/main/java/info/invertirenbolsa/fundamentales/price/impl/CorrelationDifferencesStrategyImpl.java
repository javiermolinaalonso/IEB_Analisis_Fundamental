package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CorrelationDifferencesStrategyImpl implements CorrelationStrategy {

    private static final Integer AMOUNT_OF_SHARES = 1;
    private static final Logger logger = Logger.getLogger(CorrelationDifferencesStrategyImpl.class);
    
    public List<InvestmentAction> calculateBenefit(Instant currentInstant, Instant nextInstant, Iterable<StockList> stocks) {
        //Must determine which stock to buy and sell
        Instant previousInstant = currentInstant.minus(15, ChronoUnit.DAYS);
        Iterator<StockList> it = stocks.iterator();
        StockList stockList1 = it.next();
        StockList stockList2 = it.next();
        List<InvestmentAction> actions = new ArrayList<>();
        
        StockPrice s1 = stockList1.getByInstant(previousInstant);
        StockPrice s2 = stockList2.getByInstant(previousInstant);
        BigDecimal initialDifference = s1.getValue().subtract(s2.getValue());
        try{
            
            s1 = stockList1.getByInstant(currentInstant);
            s2 = stockList2.getByInstant(currentInstant);
            BigDecimal finalDifference = s1.getValue().subtract(s2.getValue());
            
            if(finalDifference.compareTo(initialDifference) > 0){
                //S2 has more value than S1 -> Sell S2 and Buy S1
                actions.add(new InvestmentAction(s1, InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList1.getByInstant(nextInstant), InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(s2, InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList2.getByInstant(nextInstant), InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
            }else{
                //S2 has decreased value -> Buy S2 and Sell S1
                actions.add(new InvestmentAction(s1, InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList1.getByInstant(nextInstant), InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(s2, InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList2.getByInstant(nextInstant), InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
            }
        }catch(Exception e){
            logger.error("Error calculating benefit");
        }
        return actions;
    }
    
}
