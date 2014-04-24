package infor.invertirenbolsa.financials;

import info.invertirenbolsa.fundamentales.price.data.loader.DataLoader;
import info.invertirenbolsa.fundamentales.price.data.loader.DataLoaderCsv;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationIntervalInputData;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationOnlyBuyStrategyImpl;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationService;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationServiceImpl;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationStrategy;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationTwoStocks;
import info.invertirenbolsa.fundamentales.price.impl.InvestmentResult;
import info.invertirenbolsa.fundamentales.price.impl.StockList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class CovarianceLauncher {

    private static final String DEFAULT_PATH = "C:\\Users\\00556998\\Downloads\\quantquote_daily_sp500_83986\\daily";
    private static final String DEFAULT_OUTFILE = "C:\\Users\\00556998\\Downloads\\quantquote_daily_sp500_83986\\correlation";

    private static final String DEFAULT_FIRST_STOCK = "MA";
    private static final String DEFAULT_SECOND_STOCK = "V";
    
    private static final Logger logger = Logger.getLogger(CovarianceLauncher.class);
    
    //Data is available to download from https://quantquote.com/historical-stock-data
    
    public static void main(String args[]) throws Exception {
        long t = System.currentTimeMillis();
        
        DataLoader loader = new DataLoaderCsv(DEFAULT_PATH);
        loader.loadData();
        logger.info("Data loaded");
        
        Instant from = Instant.parse("2010-01-01T00:00:00.00Z");
        Instant to = Instant.parse("2013-01-01T00:00:00.00Z");
        
        StockList s1 = loader.loadStockList(DEFAULT_FIRST_STOCK);
        StockList s2 = loader.loadStockList(DEFAULT_SECOND_STOCK);
        CorrelationTwoStocks inputDataForCorrelation = new CorrelationTwoStocks(s1, s2, from, to);
        CorrelationIntervalInputData inputDataInterval = new CorrelationIntervalInputData(20, ChronoUnit.DAYS, 1, ChronoUnit.DAYS);
        
        CorrelationStrategy cStrategy = new CorrelationOnlyBuyStrategyImpl();
        CorrelationService cService = new CorrelationServiceImpl(cStrategy);
        
        List<StockCorrelation> correlationList = cService.calculateBestIntervalCorrelationInDateRangeSortedByDate(inputDataForCorrelation, inputDataInterval);
        
       
        InvestmentResult computationResult = cService.calculateCorrelationAlerts(correlationList, inputDataForCorrelation);
        computationResult.getActions().forEach(x -> logger.info(String.format("%s %s@%s$ at %s", x.getAction().toString(), x.getTicker(), x.getPrice().toString(), x.getInstant().toString())));
        logger.info(String.format("The benefit is %s%%", computationResult.getBenefit()));
        
        t = System.currentTimeMillis() - t;
        logger.info(String.format("The process took %s ms", t));
        logger.info("Exiting");
    }
    
    private static void printCorrelation(List<StockCorrelation> correlations, BigDecimal threshold, String file) throws FileNotFoundException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        PrintStream printer = new PrintStream(new File(file+".csv"));
        printer.print("Stock A, Stock B, From, To, Count, Correlation\n");
        correlations
            .stream()
            .filter(x -> x.getCorrelation().abs().compareTo(threshold) > 0)
            .forEach(x -> printer.print(String.format("%s, %s, %s, %s, %s, %s\n", 
                    x.getTickerOne(), 
                    x.getTickerTwo(), 
                    df.format(LocalDateTime.ofInstant(x.getFrom(), ZoneId.systemDefault()).toLocalDate()), 
                    df.format(LocalDateTime.ofInstant(x.getTo(), ZoneId.systemDefault()).toLocalDate()), 
                    x.getComparedValues(), 
                    x.getCorrelation().multiply(new BigDecimal(100000)).setScale(0, RoundingMode.HALF_DOWN).toString()
             )));
        printer.close();
    }

    private static StockList[][] createPairsOfStockList(Map<String, StockList> data, String... tickers) {
        Set<String> visitedStocks = new HashSet<String>();
        int i = 0;
        StockList[][] map = new StockList[(data.size() * (data.size()+1) / 2) - data.size()][2];
        
        for(String keyOne : data.keySet()){
            if(tickers.length == 0 || Arrays.asList(tickers).contains(keyOne)){
                for(String keyTwo : data.keySet()){
                    if(!keyOne.equals(keyTwo) && !visitedStocks.contains(keyTwo) && (tickers.length == 0 || Arrays.asList(tickers).contains(keyTwo))){
                        map[i][0] = data.get(keyOne);
                        map[i][1] = data.get(keyTwo);
                        i++;
                    }
                }
                visitedStocks.add(keyOne);
            }
        }
        return map;
    }

}
