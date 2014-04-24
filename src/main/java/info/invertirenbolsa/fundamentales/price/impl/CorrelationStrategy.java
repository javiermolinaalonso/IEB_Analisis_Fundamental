package info.invertirenbolsa.fundamentales.price.impl;

import java.time.Instant;
import java.util.List;

public interface CorrelationStrategy {

    List<InvestmentAction> calculateBenefit(Instant currentInstant, Instant nextInstant, Iterable<StockList> stocks);
    
}
