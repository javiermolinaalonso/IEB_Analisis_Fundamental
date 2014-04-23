package info.invertirenbolsa.fundamentales.price.impl;

import infor.invertirenbolsa.financials.StockCorrelation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public abstract class CorrelationServiceImpl {
    
    //TODO AlertStrategy + CalculateBenefitStrategy
    private static final Logger logger = Logger.getLogger(CorrelationServiceImpl.class);
    private static final Long SECONDS_PER_DAY = 86400l;

    public BigDecimal computeAlerts(Map<Integer, List<StockCorrelation>> correlationMap, StockList[][] stocks) {
        BigDecimal result = BigDecimal.ZERO;
        Long secondsInvested = 0l;
        for(Integer correlationKey : correlationMap.keySet()){
            List<StockCorrelation> correlations = correlationMap.get(correlationKey);
            List<Instant> alerts = detectAlerts(correlations);
            
            for(int i = 0; i < alerts.size(); i+=2){
                Instant currentInstant = alerts.get(i);
                Instant nextInstant = alerts.get(i+1);
                
                secondsInvested+=nextInstant.getEpochSecond() - currentInstant.getEpochSecond();
                BigDecimal benefit = calculateBenefit(currentInstant, nextInstant, stocks[0]);
                
                result = result.add(benefit);
            }
        }
        logger.info(String.format("The benefit is %s%%", result.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN)));
        logger.info(String.format("The amount of days invested is %s", new BigDecimal(secondsInvested / SECONDS_PER_DAY).setScale(2, RoundingMode.HALF_DOWN)));
        return result;
    }

    private List<Instant> detectAlerts(List<StockCorrelation> list) {
        List<Instant> alerts = new ArrayList<>();
        StatisticList sList = new StatisticList(list.stream().map(x -> x.getCorrelation()).collect(Collectors.toList()));
        BigDecimal mean = sList.getMean();
        BigDecimal stdDev = sList.getStdDev();
        BigDecimal limitValue = mean.subtract(stdDev);
        Boolean buy = true;
        for(StockCorrelation corr : list) {
            if(buy){
                if(corr.getCorrelation().compareTo(limitValue) < 0){
                    buy = !buy;
                    alerts.add(corr.getTo());
                }
            }else{
                if(corr.getCorrelation().compareTo(limitValue) > 0){
                    buy = !buy;
                    alerts.add(corr.getTo());
                }
            }
        }
        return alerts;
    }
    
    protected abstract BigDecimal calculateBenefit(Instant currentInstant, Instant nextInstant, StockList[] stocks);
    
}
