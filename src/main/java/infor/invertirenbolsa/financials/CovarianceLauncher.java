package infor.invertirenbolsa.financials;

import info.invertirenbolsa.fundamentales.price.StockService;
import info.invertirenbolsa.fundamentales.price.impl.CorrelationOnlyBuyServiceImpl;
import info.invertirenbolsa.fundamentales.price.impl.StatisticList;
import info.invertirenbolsa.fundamentales.price.impl.StockList;
import info.invertirenbolsa.fundamentales.price.impl.StockPrice;
import info.invertirenbolsa.fundamentales.price.impl.StockServiceMultithreadImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class CovarianceLauncher {

    private static final String DEFAULT_PATH = "C:\\Users\\00556998\\Downloads\\quantquote_daily_sp500_83986\\daily";
    
    private static final String DEFAULT_OUTFILE = "C:\\Users\\00556998\\Downloads\\quantquote_daily_sp500_83986\\correlation";
    
    private static final Logger logger = Logger.getLogger(CovarianceLauncher.class);
    //Data is available to download from https://quantquote.com/historical-stock-data
    public static void main(String args[]) throws Exception {
        long t = System.currentTimeMillis();
        Map<String, StockList> data = loadData(DEFAULT_PATH, 0);
     
        logger.info("Data loaded");
        
        StockList[][] stocks = createPairsOfStockList(data, "MA", "V");
        
        logger.info("Vector created");
        
        Instant from = Instant.parse("2010-01-01T00:00:00.00Z");
        Instant to = Instant.parse("2013-01-01T00:00:00.00Z");
//        processCorrelations(stocks, from, to);
        
        Map<Integer, List<StockCorrelation>> correlationMap = processEvolution(stocks[0][0], stocks[0][1], from, to);
        
        BigDecimal computationResult = new CorrelationOnlyBuyServiceImpl().computeAlerts(correlationMap, stocks);
        
        logger.info(String.format("The final benefit is %s%%", computationResult.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN)));
        t = System.currentTimeMillis() - t;
        logger.info(String.format("The process took %s ms", t));
        logger.info("Exiting");
    }
    
    private static Map<Integer, List<StockCorrelation>> processEvolution(StockList s1, StockList s2, Instant from, Instant to) throws FileNotFoundException {
        Map<Integer, List<StockCorrelation>> correlationMap = new HashMap<>();
        correlationMap.put(15, processEvolution(s1, s2, from, to, 15));
//        correlationMap.put(30, processEvolution(s1, s2, from, to, 30));
//        correlationMap.put(90, processEvolution(s1, s2, from, to, 90));
//        correlationMap.put(365, processEvolution(s1, s2, from, to, 365));
        logger.info("Evolution computation finished");
        return correlationMap;
    }
    
    private static List<StockCorrelation> processEvolution(StockList s1, StockList s2, Instant from, Instant to, Integer i) throws FileNotFoundException {
        StockService s = new StockServiceMultithreadImpl();
        List<StockCorrelation> stockList = s.computeBestIntervalCorrelation(s1, s2, from, to, i, ChronoUnit.DAYS, 1, ChronoUnit.DAYS);
        stockList.sort((x, y) -> x.getFrom().compareTo(y.getFrom()));
        printCorrelation(stockList, new BigDecimal(0.0d), DEFAULT_OUTFILE+i.toString());
        return stockList;
    }

    private static void processCorrelations(StockList[][] stocks, Instant from, Instant to) throws FileNotFoundException {
      List<StockCorrelation> correlations = new StockServiceMultithreadImpl().processCorrelations(stocks, from, to);
      
      logger.info("Correlation computation finished");
      
      printCorrelation(correlations, new BigDecimal(0.9d), DEFAULT_OUTFILE);
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

    private static Map<String, StockList> loadData(String path, Integer amount) throws Exception{
        Map<String, StockList> map = new HashMap<String, StockList>();
        File directory = new File(path);
        
        if(amount.equals(0)){
            amount = directory.listFiles().length;
        }
        for(int i = 0; i < amount; i++){
            File file = directory.listFiles()[i];
            String ticker = file.getName().split("_")[1].split("\\.")[0].toUpperCase();
            map.put(ticker, loadList(ticker, file.getAbsolutePath()));
        }
        
        return map;
    }
    
    private static StockList loadList(String ticker, String file) throws Exception {
        StockList prices = new StockList(ticker);
        for(String[] value : readCsv(file)){
            prices.add(new StockPrice(ticker, Instant.ofEpochMilli(new SimpleDateFormat("yyyyMMdd").parse(value[0]).getTime()), new BigDecimal(value[2])));
        }
        return prices;
    }
    
    private static List<String[]> readCsv(String file) throws Exception {
        List<String[]> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));
        String line = br.readLine();
        while (line != null) {
            list.add(line.split(","));
            line = br.readLine();
        }
        br.close();
        return list;
    }
}
