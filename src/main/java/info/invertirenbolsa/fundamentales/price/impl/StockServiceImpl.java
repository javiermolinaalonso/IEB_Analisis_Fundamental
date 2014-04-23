package info.invertirenbolsa.fundamentales.price.impl;

import info.invertirenbolsa.fundamentales.price.StockService;
import info.invertirenbolsa.fundamentales.price.exceptions.EmptyStatisticListException;
import infor.invertirenbolsa.financials.StockCorrelation;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class StockServiceImpl implements StockService {

    private Logger logger = Logger.getLogger(StockServiceImpl.class);
    
    @Override
    public StockCorrelation computeCorrelation(StockList firstStock, StockList secondStock, Instant from, Instant to) {
        if(firstStock == null || secondStock == null){
            throw new EmptyStatisticListException();
        }
        StockList firstListFiltered = firstStock.filterStocksAndSort(secondStock, from, to);
        StockList secondListFiltered = secondStock.filterStocksAndSort(firstStock, from, to);
        StatisticList firstStockValues = new StatisticList(firstListFiltered.stream().map(x -> x.getValue()).collect(Collectors.toList()));
        StatisticList secondStockValues = new StatisticList(secondListFiltered.stream().map(x -> x.getValue()).collect(Collectors.toList()));
        return new StockCorrelation(firstStock.getTicker(), secondStock.getTicker(), firstStockValues.getCorrelation(secondStockValues), firstListFiltered.getFirst().getInstant(), firstListFiltered.getLast().getInstant(), firstListFiltered.size());
    }
    
    @Override
    public StockCorrelation computeCorrelation(StockList firstStock, StockList secondStock) {
        return computeCorrelation(firstStock, secondStock, null, null);
    }

    @Override
    public List<StockCorrelation> processCorrelations(StockList[][] stocks) {
        return processCorrelations(stocks, null, null);
    }

    @Override
    public List<StockCorrelation> processCorrelations(StockList[][] stocks, Instant from, Instant to) {
        List<StockCorrelation> correlations = new ArrayList<>();
        for(int i = 0; i < stocks.length; i++){
            try{
                StockCorrelation cor = computeCorrelation(stocks[i][0], stocks[i][1], from, to);
                correlations.add(cor);
                logger.debug(cor.toString());
            }catch(EmptyStatisticListException e){
                logger.info(String.format("The stock %s does not have interesection against %s", stocks[i][0].getTicker(), stocks[i][1].getTicker()));
            }
        }
        
        Collections.sort(correlations);
        return correlations;
    }

    @Override
    public List<StockCorrelation> computeBestIntervalCorrelation(StockList s1, StockList s2, Instant from, Instant to, Integer intervalAmount, TemporalUnit unit, Integer stepAmount, TemporalUnit stepUnit) {
        Instant currentFrom = Instant.from(from);
        Instant currentTo = Instant.from(from).plus(intervalAmount, unit);
        
        List<StockCorrelation> correlations = new ArrayList<>();
        while(currentTo.isBefore(to)){
            StockCorrelation correlation = computeCorrelation(s1, s2, currentFrom, currentTo);
            logger.debug(correlation.toString());
            if(!correlations.parallelStream().anyMatch(x -> x.getFrom().equals(correlation.getFrom()))) {
                correlations.add(correlation);
            }
            currentFrom = currentFrom.plus(stepAmount, stepUnit);
            currentTo = currentTo.plus(stepAmount, stepUnit);
        }
        
        correlations.sort((x, y) -> x.getCorrelation().compareTo(y.getCorrelation()));
        return correlations;
    }
    
}
