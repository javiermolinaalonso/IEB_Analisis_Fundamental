package info.invertirenbolsa.fundamentales.price.impl;

import infor.invertirenbolsa.financials.StockCorrelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StockServiceMultithreadImpl extends StockServiceImpl {

    private List<Future<StockCorrelation>> futureCorrelations = new ArrayList<>(); //Shared list
    
    @Override
    public List<StockCorrelation> processCorrelations(StockList[][] stocks) {
        futureCorrelations = new ArrayList<Future<StockCorrelation>>();
        BlockingQueue<Runnable> waitingQueue = new ArrayBlockingQueue<Runnable>(stocks.length);
        ExecutorService executor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.MINUTES, waitingQueue);
        for(int i = 0; i < stocks.length; i++){
            futureCorrelations.add(executor.submit(new CovarianceCalculator(stocks[i][0], stocks[i][1])));
        }
        List<StockCorrelation> correlations = new ArrayList<>();
        for(Future<StockCorrelation> c : futureCorrelations){
            try {
                correlations.add(c.get());
            } catch (InterruptedException | ExecutionException e) {
                
            }
        }
        executor.shutdown();
        Collections.sort(correlations);
        return correlations;
    }

    private class CovarianceCalculator implements Callable<StockCorrelation> {
        private StockList s1;
        private StockList s2;
        
        public CovarianceCalculator(StockList s1, StockList s2){
            this.s1 = s1;
            this.s2 = s2;
        }
        @Override
        public StockCorrelation call() {
            return computeCorrelation(s1, s2);
        }
        
    }
}
