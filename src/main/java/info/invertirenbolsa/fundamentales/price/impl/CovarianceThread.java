package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Callable;

public class CovarianceThread implements Callable<BigDecimal> {

    private List<BigDecimal> X; 
    private List<BigDecimal> Y; 
    private BigDecimal xMean; 
    private BigDecimal yMean; 
    private Integer N;
    private Integer from;
    private Integer to;
    
    public CovarianceThread(List<BigDecimal> x, List<BigDecimal> y, BigDecimal xMean, BigDecimal yMean, Integer n, Integer from, Integer to) {
        super();
        this.X = x;
        this.Y = y;
        this.xMean = xMean;
        this.yMean = yMean;
        this.N = n;
        this.from = from;
        this.to = to;
    }



    @Override
    public BigDecimal call() throws Exception {
        BigDecimal sum = new BigDecimal(0);
        for(int i = from; i < to; i++) {
            sum = sum.add((X.get(i).subtract(xMean)).multiply(Y.get(i).subtract(yMean)).divide(new BigDecimal(N), 5, RoundingMode.HALF_DOWN));
        }
        return sum;
    }

}
