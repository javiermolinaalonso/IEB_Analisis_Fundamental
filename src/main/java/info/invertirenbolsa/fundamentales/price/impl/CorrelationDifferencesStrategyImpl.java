package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CorrelationDifferencesStrategyImpl implements CorrelationStrategy {

    private static final Integer AMOUNT_OF_SHARES = 1;
    private static final Logger logger = Logger.getLogger(CorrelationDifferencesStrategyImpl.class);
    
    public List<InvestmentAction> calculateBenefit(Instant currentInstant, Instant nextInstant, Iterable<StockList> stocks) {
        //Must determine which stock to buy and sell
        Iterator<StockList> it = stocks.iterator();
        StockList stockList1 = it.next();
        StockList stockList2 = it.next();
        List<InvestmentAction> actions = new ArrayList<>();
        
        try{
            StockPrice s1 = loadInstant(stockList1, currentInstant, nextInstant);
            StockPrice currentS1Stock = stockList1.getByInstant(currentInstant);
            BigDecimal s1IncreasePercent = currentS1Stock.getValue().subtract(s1.getValue()).divide(s1.getValue(), 5, RoundingMode.HALF_DOWN);
            
            StockPrice s2 = loadInstant(stockList2, currentInstant, nextInstant);
            StockPrice currentS2Stock = stockList2.getByInstant(currentInstant);
            BigDecimal s2IncreasePercent = currentS2Stock.getValue().subtract(s2.getValue()).divide(s2.getValue(), 5, RoundingMode.HALF_DOWN);
            
            if(s1IncreasePercent.compareTo(s2IncreasePercent) <= 0){
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
