package info.invertirenbolsa.fundamentales.price.impl;

import infor.invertirenbolsa.financials.StockCorrelation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class CorrelationServiceImpl implements CorrelationService {
    
    //TODO AlertStrategy + CalculateBenefitStrategy
    private static final Logger logger = Logger.getLogger(CorrelationServiceImpl.class);

    private CorrelationStrategy correlationStrategy;
    
    public CorrelationServiceImpl(){
        super();
    }
    
    public CorrelationServiceImpl(CorrelationStrategy correlationStrategy){
        this.correlationStrategy = correlationStrategy;
    }
    
    @Override
    public Map<CorrelationIntervalInputData, List<StockCorrelation>> computeCorrelationBetweenTwoStocks(CorrelationTwoStocks inputData, Iterable<CorrelationIntervalInputData> intervals) {
        Map<CorrelationIntervalInputData, List<StockCorrelation>> correlationMap = new HashMap<>();
        
        for (CorrelationIntervalInputData intervalData : intervals) {
            correlationMap.put(intervalData, calculateBestIntervalCorrelationInDateRangeSortedByDate(inputData, intervalData));
        }
        
        return correlationMap;
    }
    
    public List<StockCorrelation> calculateBestIntervalCorrelationInDateRangeSortedByDate(CorrelationTwoStocks inputData, CorrelationIntervalInputData correlationInterval) {
        List<StockCorrelation> stockList = calculateBestIntervalCorrelationInDateRange(inputData, correlationInterval);
        stockList.sort((x, y) -> x.getFrom().compareTo(y.getFrom()));
        return stockList;
    }
    
    /**
     * Given two stocks, return the best interval correlation in the date range
     */
    private List<StockCorrelation> calculateBestIntervalCorrelationInDateRange(CorrelationTwoStocks stockData, CorrelationIntervalInputData intervalData){
        Instant currentFrom = Instant.from(stockData.getFrom());
        Instant currentTo = Instant.from(stockData.getFrom()).plus(intervalData.getInterval(), intervalData.getIntervalUnit());
        
        List<StockCorrelation> correlations = new ArrayList<>();
        while(currentTo.isBefore(stockData.getTo())){
            CorrelationTwoStocks correlationStockData = new CorrelationTwoStocks(stockData.getS1(), stockData.getS2(), currentFrom, currentTo);
            
            StockCorrelation correlation = correlationStockData.calculateCorrelation();
            if(!correlations.parallelStream().anyMatch(x -> x.getFrom().equals(correlation.getFrom()))) {
                correlations.add(correlation);
            }
            currentFrom = currentFrom.plus(intervalData.getStep(), intervalData.getStepUnit());
            currentTo = currentTo.plus(intervalData.getStep(), intervalData.getStepUnit());
        }
        
        return correlations;
    }
    
    public InvestmentResult calculateCorrelationAlerts(List<StockCorrelation> correlations, CorrelationTwoStocks stockData) {
        Long secondsInvested = 0l;
        List<Instant> alerts = detectAlerts(correlations);
        InvestmentActions investmentActions = new InvestmentActions();
        for(int i = 0; i < alerts.size(); i+=2){
            Instant currentInstant = alerts.get(i);
            Instant nextInstant = alerts.get(i+1);
            
            secondsInvested+=nextInstant.getEpochSecond() - currentInstant.getEpochSecond();
            List<InvestmentAction> actions = correlationStrategy.calculateBenefit(currentInstant, nextInstant, Arrays.asList(stockData.getS1(), stockData.getS2()));
            investmentActions.addAll(actions);
        }
        InvestmentResult result = new InvestmentResult(stockData.getFrom(), stockData.getTo(), investmentActions);
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

    public void setCorrelationStrategy(CorrelationStrategy correlationStrategy) {
        this.correlationStrategy = correlationStrategy;
    }

}
