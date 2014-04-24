package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CorrelationOnlyBuyStrategyImpl implements CorrelationStrategy {

    private static final Integer AMOUNT_OF_SHARES = 1;
    
    private static final Logger logger = Logger.getLogger(CorrelationOnlyBuyStrategyImpl.class);
    
    @Override
    public List<InvestmentAction> calculateBenefit(Instant currentInstant, Instant nextInstant, Iterable<StockList> stocks) {
      //Must determine which stock to buy and sell
        Iterator<StockList> it = stocks.iterator();
        StockList stockList1 = it.next();
        StockList stockList2 = it.next();
        List<InvestmentAction> actions = new ArrayList<>();
        try{
            StockPrice s1 = loadInstant(stockList1, currentInstant);
            StockPrice currentS1Stock = stockList1.getByInstant(currentInstant);
            BigDecimal s1IncreasePercent = currentS1Stock.getValue().subtract(s1.getValue()).divide(s1.getValue(), 5, RoundingMode.HALF_DOWN);
            
            StockPrice s2 = loadInstant(stockList2, currentInstant);
            StockPrice currentS2Stock = stockList2.getByInstant(currentInstant);
            BigDecimal s2IncreasePercent = currentS2Stock.getValue().subtract(s2.getValue()).divide(s2.getValue(), 5, RoundingMode.HALF_DOWN);
            
            if(s1IncreasePercent.compareTo(s2IncreasePercent) <= 0){
                //S2 have been incremented more than s1, so buy s1
                actions.add(new InvestmentAction(currentS1Stock, InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList1.getByInstant(nextInstant), InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
            }else{
                actions.add(new InvestmentAction(currentS2Stock, InvestmentActionEnum.BUY, AMOUNT_OF_SHARES));
                actions.add(new InvestmentAction(stockList2.getByInstant(nextInstant), InvestmentActionEnum.SELL, AMOUNT_OF_SHARES));
            }
        }catch(Exception e){
            logger.error("Error calculating benefit");
        }
        return actions;
    }

    private StockPrice loadInstant(StockList stockList, Instant instant) {
        Instant previousInstant = instant.minus(5, ChronoUnit.DAYS); //TODO esto es un poco manga...
        StockPrice s = stockList.getByInstant(previousInstant);
        int attempts = 10;
        while (s == null && attempts > 0){
            previousInstant = previousInstant.minus(1, ChronoUnit.DAYS);
            s = stockList.getByInstant(previousInstant);
            attempts--;
        }
        return s;
    }
    
}
