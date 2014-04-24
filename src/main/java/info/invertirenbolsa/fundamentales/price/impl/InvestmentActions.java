package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InvestmentActions {
    
    private Long secondsInvested;
    private BigDecimal benefit;
    private List<InvestmentAction> actions;
    private Map<String, List<InvestmentAction>> actionsPerTicker;
    
    public InvestmentActions(){
        this.actions = new ArrayList<InvestmentAction>();
        this.actionsPerTicker = new HashMap<String, List<InvestmentAction>>();
        this.secondsInvested = 0l;
        this.benefit = BigDecimal.ZERO;
    }
    
    public InvestmentActions addAll(List<InvestmentAction> actions2) {
        actions2.forEach(x -> add(x));
        return this;
    }
    
    public InvestmentActions add(InvestmentAction action){
        this.actions.add(action);
        this.actionsPerTicker.putIfAbsent(action.getTicker(), new ArrayList<>());
        this.actionsPerTicker.get(action.getTicker()).add(action);
        updateBenefit();
        return this;
    }
    
    private void updateBenefit(){
        benefit = BigDecimal.ZERO;
        for(String ticker : actionsPerTicker.keySet()){
            List<InvestmentAction> actions = actionsPerTicker.get(ticker);
            benefit = benefit.add(computeBenefit(actions));
        }
    }

    private BigDecimal computeBenefit(List<InvestmentAction> actionsTicker) {
        BigDecimal tickerBenefit = BigDecimal.ZERO;
        for(InvestmentAction action : actionsTicker){
            tickerBenefit = tickerBenefit.add(action.getAmountInvested().negate());
        }
        return tickerBenefit;
    }

    public BigDecimal getBenefit() {
        return this.benefit;
    }

    public List<InvestmentAction> getActions() {
        return this.actions;
    }

    public BigDecimal getAmountInvested() {
        Optional<BigDecimal> value = this.actions.parallelStream().map(x -> InvestmentActionEnum.BUY.equals(x.getAction()) ? x.getAmountInvested() : BigDecimal.ZERO).reduce( (x, y) -> y = y.add(x));
        
        if(value.isPresent()){
            return value.get();
        }else{
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getProfitability() {
        BigDecimal amountInvested = getAmountInvested();
        if(amountInvested.compareTo(BigDecimal.ZERO) != 0){
            return getBenefit().divide(amountInvested, 5, RoundingMode.HALF_DOWN);
        }else{
            return BigDecimal.ZERO;
        }
    }

}
