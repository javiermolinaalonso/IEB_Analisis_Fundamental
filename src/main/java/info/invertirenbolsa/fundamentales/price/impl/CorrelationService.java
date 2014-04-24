package info.invertirenbolsa.fundamentales.price.impl;

import infor.invertirenbolsa.financials.StockCorrelation;

import java.util.List;
import java.util.Map;

public interface CorrelationService {

    Map<CorrelationIntervalInputData, List<StockCorrelation>> computeCorrelationBetweenTwoStocks(CorrelationTwoStocks inputData, Iterable<CorrelationIntervalInputData> intervals);
    
    List<StockCorrelation> calculateBestIntervalCorrelationInDateRangeSortedByDate(CorrelationTwoStocks stockData, CorrelationIntervalInputData intervalData);
    
    public InvestmentResult calculateCorrelationAlerts(List<StockCorrelation> correlations, CorrelationTwoStocks stockData, CorrelationIntervalInputData inputDataInterval);
}
