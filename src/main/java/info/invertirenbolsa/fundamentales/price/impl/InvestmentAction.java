package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.time.Instant;

public class InvestmentAction {

    private final String ticker;
    private final InvestmentActionEnum action;
    private final Instant instant;
    private final BigDecimal price;
    private final Integer sharesAmount;
    private final BigDecimal amountInvested;
    
    public InvestmentAction(String ticker, InvestmentActionEnum action, Instant instant, BigDecimal price, Integer sharesAmount) {
        super();
        this.ticker = ticker;
        this.action = action;
        this.instant = instant;
        this.price = price;
        this.sharesAmount = sharesAmount;
        if(InvestmentActionEnum.SELL.equals(action)){
            this.amountInvested = price.multiply(new BigDecimal(sharesAmount)).multiply(new BigDecimal(-1));
        }else{
            this.amountInvested = price.multiply(new BigDecimal(sharesAmount));
        }
    }

    public InvestmentAction(StockPrice stock, InvestmentActionEnum action, Integer sharesAmount){
        this(stock.getTicker(), action, stock.getInstant(), stock.getValue(), sharesAmount);
    }

    public InvestmentActionEnum getAction() {
        return this.action;
    }
    public Instant getInstant() {
        return this.instant;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getTicker() {
        return this.ticker;
    }

    public Integer getSharesAmount() {
        return this.sharesAmount;
    }

    public BigDecimal getAmountInvested() {
        return amountInvested;
    }
    
    
}
