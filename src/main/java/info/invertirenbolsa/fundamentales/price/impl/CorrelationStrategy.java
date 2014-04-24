package info.invertirenbolsa.fundamentales.price.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface CorrelationStrategy {
    
    static final int HOURS_PER_DAY = 24;
    static final int DAYS_TO_LOAD = 7;

    List<InvestmentAction> calculateBenefit(Instant currentInstant, Instant nextInstant, Iterable<StockList> stocks);
    
    default StockPrice loadInstant(StockList stockList, Instant instant, Instant nextInstant) throws StockNotFoundException {
        long diff = nextInstant.getEpochSecond() - instant.getEpochSecond();
        Instant previousInstant = instant.minus(diff, ChronoUnit.SECONDS); //TODO esto es un poco manga...
        StockPrice s = stockList.getByInstant(previousInstant);
        int attempts = HOURS_PER_DAY * DAYS_TO_LOAD; //24hours per day
        while (s == null && attempts > 0){
            previousInstant = previousInstant.minus(1, ChronoUnit.HOURS);
            s = stockList.getByInstant(previousInstant);
            attempts--;
        }
        if(s == null){
            throw new StockNotFoundException();
        }
        return s;
    }
    
}
