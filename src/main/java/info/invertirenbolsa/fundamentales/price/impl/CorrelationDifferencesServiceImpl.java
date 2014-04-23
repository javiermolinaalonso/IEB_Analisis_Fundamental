package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;

public class CorrelationDifferencesServiceImpl extends CorrelationServiceImpl {

    private static final Logger logger = Logger.getLogger(CorrelationDifferencesServiceImpl.class);
    
    protected BigDecimal calculateBenefit(Instant currentInstant, Instant nextInstant, StockList[] stocks) {
        //Must determine which stock to buy and sell
        Instant previousInstant = currentInstant.minus(15, ChronoUnit.DAYS);
        try{
            StockPrice s1 = stocks[0].stream().filter(x -> x.getInstant().equals(previousInstant)).iterator().next();
            StockPrice s2 = stocks[1].stream().filter(x -> x.getInstant().equals(previousInstant)).iterator().next();
            BigDecimal initialDifference = s1.getValue().subtract(s2.getValue());
            
            s1 = stocks[0].stream().filter(x -> x.getInstant().equals(currentInstant)).iterator().next();
            s2 = stocks[1].stream().filter(x -> x.getInstant().equals(currentInstant)).iterator().next();
            BigDecimal finalDifference = s1.getValue().subtract(s2.getValue());
            
            StockPrice s1Buy;
            StockPrice s2Sell;
            StockPrice s1Sell;
            StockPrice s2Buy;
            if(finalDifference.compareTo(initialDifference) > 0){
                //S2 has more value than S1 -> Sell S2 and Buy S1
                s1Buy = s1;
                s2Sell = s2;
                s1Sell = stocks[0].stream().filter(x -> x.getInstant().equals(nextInstant)).iterator().next();
                s2Buy = stocks[1].stream().filter(x -> x.getInstant().equals(nextInstant)).iterator().next();
            }else{
                //S2 has decreased value -> Buy S2 and Sell S1
                s1Buy = s2;
                s2Sell = s1;
                s1Sell = stocks[1].stream().filter(x -> x.getInstant().equals(nextInstant)).iterator().next();
                s2Buy = stocks[0].stream().filter(x -> x.getInstant().equals(nextInstant)).iterator().next();
            }
            logger.info(String.format("Bought %s at %s and sold %s at %s.", s1Buy.getTicker(), s1Buy.getValue().toString(), s2Sell.getTicker(), s2Sell.getValue().toString()));
            logger.info(String.format("Sold %s at %s and bought %s at %s.", s1Sell.getTicker(), s1Sell.getValue().toString(), s2Buy.getTicker(), s2Buy.getValue().toString()));
    
            BigDecimal s1benefit = (s1Sell.getValue().subtract(s1Buy.getValue())).divide(s1Buy.getValue(), 3, RoundingMode.HALF_DOWN);
            BigDecimal s2benefit = (s2Sell.getValue().subtract(s2Buy.getValue())).divide(s2Sell.getValue(), 3, RoundingMode.HALF_DOWN);
            BigDecimal operationBenefit = s1benefit.add(s2benefit);
            return operationBenefit;
        }catch(Exception e){
            return BigDecimal.ZERO;
        }
    }
    
}
