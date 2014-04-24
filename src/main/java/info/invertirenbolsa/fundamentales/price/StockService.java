package info.invertirenbolsa.fundamentales.price;

import info.invertirenbolsa.fundamentales.price.impl.CorrelationTwoStocks;
import info.invertirenbolsa.fundamentales.price.impl.StockList;
import infor.invertirenbolsa.financials.StockCorrelation;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.List;

public interface StockService {

    public StockCorrelation computeCorrelation(StockList firstStock, StockList secondStock);
    
    public StockCorrelation computeCorrelation(StockList firstStock, StockList secondStock, Instant from, Instant to);

    public List<StockCorrelation> processCorrelations(StockList[][] stocks);
    
    public List<StockCorrelation> processCorrelations(StockList[][] stocks, Instant from, Instant to);
    
}
