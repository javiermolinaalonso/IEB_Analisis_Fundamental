package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StockList extends LinkedList<StockPrice> {

    private static final long serialVersionUID = 4376347809602759884L;
    private final String ticker;
    
    public StockList(String ticker){
        this(new ArrayList<StockPrice>(), ticker);
    }
    public StockList(List<StockPrice> origin, String ticker){
        super(origin.stream().sorted( (y, z) -> y.getInstant().compareTo(z.getInstant()) ).collect(Collectors.toList()));
        this.ticker = ticker;
    }
    
    public StockList filterStocksAndSort(StockList secondStock) {
        return filterStocksAndSort(secondStock, null, null);
    }
    
    public StockList filterStocksAndSort(StockList secondStock, Instant from, Instant to) {
        List<Instant> secondStockInstants = secondStock.stream().map(y -> y.getInstant()).collect(Collectors.toList());
        return new StockList(parallelStream()
                .filter(x -> secondStockInstants.contains(x.getInstant()))
                .filter(x -> from != null ? x.getInstant().compareTo(from) >= 0 : true)
                .filter(x -> from != null ? x.getInstant().compareTo(to) <= 0 : true)
                .sequential()
                .sorted((y, z) -> y.getInstant().compareTo(z.getInstant()))
                .collect(Collectors.toList()), getTicker());
    }
    
    public StockList getMean(Integer sessions){
        StockList list = new StockList(getTicker());
        for(int i = 0; i <= size() - sessions; i++){
            BigDecimal mean = new StatisticList(stream().skip(i).limit(sessions - 1).map(x -> x.getValue()).collect(Collectors.toList())).getMean();
            list.add(new StockPrice(getTicker(), get(i + sessions - 1).getInstant(), mean));
        }
        return list;
    }
    
    public String getTicker() {
        return this.ticker;
    }
    
}
