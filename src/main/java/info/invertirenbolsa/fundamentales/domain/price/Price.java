package info.invertirenbolsa.fundamentales.domain.price;

import java.math.BigDecimal;
import java.util.Currency;

public class Price {

    private final String ticker;
    private final long timestamp;
    private final Currency currency;
    private final BigDecimal price;
    
    public Price(String ticker, long timestamp, Currency value, BigDecimal price) {
        super();
        this.ticker = ticker;
        this.timestamp = timestamp;
        this.currency = value;
        this.price = price;
    }

    public String getTicker() {
        return this.ticker;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Currency getValue() {
        return this.currency;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    
}
