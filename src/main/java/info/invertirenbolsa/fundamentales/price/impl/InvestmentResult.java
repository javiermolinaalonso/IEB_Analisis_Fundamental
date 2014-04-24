package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

public class InvestmentResult {

    private final Instant from;
    private final Instant to;
    private final InvestmentActions investmentActions;
    private final CorrelationIntervalInputData inputDataInterval;
    
    public InvestmentResult(Instant from, Instant to, InvestmentActions investmentActions, CorrelationIntervalInputData inputDataInterval) {
        super();
        this.from = from;
        this.to = to;
        this.investmentActions = investmentActions;
        this.inputDataInterval = inputDataInterval;
    }

    public Instant getFrom() {
        return this.from;
    }

    public Instant getTo() {
        return this.to;
    }

    public BigDecimal getBenefit(){
        return this.investmentActions.getBenefit();
    }
    
    public List<InvestmentAction> getActions(){
        return this.investmentActions.getActions();
    }

    public BigDecimal getAmountInvested() {
        return this.investmentActions.getAmountInvested();
    }
    
    public BigDecimal getProfitabilityPercent() {
        return this.investmentActions.getProfitability().multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN);
    }

    public CorrelationIntervalInputData getInputDataInterval() {
        return this.inputDataInterval;
    }
    
}
