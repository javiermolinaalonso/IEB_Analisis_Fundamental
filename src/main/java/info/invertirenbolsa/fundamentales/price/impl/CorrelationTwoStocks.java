package info.invertirenbolsa.fundamentales.price.impl;

import info.invertirenbolsa.fundamentales.price.exceptions.EmptyStatisticListException;
import info.invertirenbolsa.fundamentales.price.exceptions.InvalidCorrelationDatesException;
import infor.invertirenbolsa.financials.StockCorrelation;

import java.time.Instant;
import java.util.stream.Collectors;

public final class CorrelationTwoStocks {
    
    private final StockList s1;
    private final StockList s2;
    private final Instant from;
    private final Instant to;
    
    public CorrelationTwoStocks(StockList s1, StockList s2, Instant from, Instant to) {
        super();
        this.s1 = s1;
        this.s2 = s2;
        this.from = from;
        this.to = to;
    }
    public StockList getS1() {
        return this.s1;
    }
    public StockList getS2() {
        return this.s2;
    }
    public Instant getFrom() {
        return this.from;
    }
    public Instant getTo() {
        return this.to;
    }
    public StockCorrelation calculateCorrelation() {
        if(s1 == null || s2 == null){
            throw new EmptyStatisticListException();
        }
        if(from.isAfter(to)){
            throw new InvalidCorrelationDatesException();
        }
        StockList firstListFiltered = s1.filterStocksAndSort(s2, from, to);
        StockList secondListFiltered = s2.filterStocksAndSort(s1, from, to);
        StatisticList firstStockValues = new StatisticList(firstListFiltered.stream().map(x -> x.getValue()).collect(Collectors.toList()));
        StatisticList secondStockValues = new StatisticList(secondListFiltered.stream().map(x -> x.getValue()).collect(Collectors.toList()));
        return new StockCorrelation(s1.getTicker(), s2.getTicker(), firstStockValues.getCorrelation(secondStockValues), firstListFiltered.getFirst().getInstant(), firstListFiltered.getLast().getInstant(), firstListFiltered.size());
    }
    
}
