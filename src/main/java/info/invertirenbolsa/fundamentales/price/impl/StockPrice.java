package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.time.Instant;

public class StockPrice {

    private final String ticker;
    private final Instant instant;
    private final BigDecimal value;
    
    public StockPrice(String ticker, Instant instant, BigDecimal value) {
        super();
        this.ticker = ticker;
        this.instant = instant;
        this.value = value;
    }

    public String getTicker() {
        return this.ticker;
    }

    public Instant getInstant() {
        return this.instant;
    }

    public BigDecimal getValue() {
        return this.value;
    }
    
    
}
