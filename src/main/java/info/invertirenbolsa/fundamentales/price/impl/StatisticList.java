package info.invertirenbolsa.fundamentales.price.impl;

import info.invertirenbolsa.fundamentales.price.exceptions.EmptyStatisticListException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class StatisticList extends ArrayList<BigDecimal> {

    private static final long serialVersionUID = -9037106671033928589L;
    private static final BigDecimal SPLITS = new BigDecimal(999999999);
    
    public StatisticList(){
        super();
    }
    public StatisticList(List<BigDecimal> origin){
        super(origin);
    }
    
    public BigDecimal getMean() {
        return stream().reduce((x, y) -> x.add(y)).get().divide(new BigDecimal(size()), 5, RoundingMode.HALF_DOWN);
    }
    
    public BigDecimal getHighest() {
        return stream().max((a, b) -> a.compareTo(b)).get();
    }

    public BigDecimal getLowest() {
        return stream().min((a, b) -> a.compareTo(b)).get();
    }
    
    public BigDecimal getStdDev() {
        BigDecimal mean = getMean();
//        return Math.sqrt(stream().map(x -> x.subtract(mean).pow(2)).reduce((x, y) -> x.add(y)).get().divide(BigDecimal.valueOf(size() - 1), 5, RoundingMode.HALF_DOWN).doubleValue());
        return new BigDecimal(Math.sqrt(stream().map(x -> x.subtract(mean).pow(2)).reduce((x, y) -> x.add(y)).get().doubleValue() / size()));
    }
    
    public BigDecimal getCorrelation(StatisticList otherList){
        BigDecimal dividend = getStdDev().multiply(otherList.getStdDev());
        if(dividend.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        }
        return getCovariance(otherList).divide(dividend, 5, RoundingMode.HALF_DOWN);
    }
    
    public BigDecimal getCovariance(StatisticList otherList) {
        if(isEmpty() || otherList.isEmpty()){
            throw new EmptyStatisticListException();
        }
        BigDecimal xMean = getMean();
        BigDecimal yMean = otherList.getMean();
        BigDecimal N = new BigDecimal(Math.min(size(), otherList.size()));
        if(N.compareTo(SPLITS) > 0){
            return calculateCovarianceMultithread(xMean, yMean, N.intValue(), otherList);
        }else{
            return calculateCovariance(xMean, yMean, N.intValue(), otherList);
        }
    }

    
    private BigDecimal calculateCovariance(BigDecimal xMean, BigDecimal yMean, Integer N, StatisticList otherList){
        BigDecimal sum = new BigDecimal(0);
        for(int i = 0; i < N.intValue(); i++) {
            sum = sum.add((get(i).subtract(xMean)).multiply(otherList.get(i).subtract(yMean)).divide(new BigDecimal(N), 5, RoundingMode.HALF_DOWN));
        }
        return sum;
    }
    
    private BigDecimal calculateCovarianceMultithread(BigDecimal xMean, BigDecimal yMean, Integer N, StatisticList otherList) {
        Vector<BigDecimal> results = mapToList(xMean, yMean, N, otherList);        
        return reduceToResult(results);
    }

    private BigDecimal reduceToResult(Vector<BigDecimal> results) {
        BigDecimal sum = new BigDecimal(0);
        for(BigDecimal partialResult : results){
            sum = sum.add(partialResult);
        }
        return sum;
    }

    private Vector<BigDecimal> mapToList(BigDecimal xMean, BigDecimal yMean, Integer N, StatisticList otherList) {
        Integer divisions = 16;
        Integer i = 0;
        Integer from = 0;
        Integer step = N / divisions;
        Vector<Future<BigDecimal>> futureResults = new Vector<>(divisions+1);
        ExecutorService service = Executors.newCachedThreadPool();
        do{
            Integer to = from + step;
            CovarianceThread th = new CovarianceThread(this, otherList, xMean, yMean, N, from, to);
            futureResults.add(i, service.submit(th));
            from = to;
            
            i++;
        }while(i < divisions);
        
        //Complete the tail
        CovarianceThread th = new CovarianceThread(this, otherList, xMean, yMean, N, from, N);
        futureResults.add(divisions, service.submit(th));
        Vector<BigDecimal> results = new Vector<BigDecimal>();
        for(Future<BigDecimal> r : futureResults){
            try {
                results.add(r.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
        return results;
    }
}
