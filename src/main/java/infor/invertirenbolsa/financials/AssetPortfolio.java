package infor.invertirenbolsa.financials;

import info.invertirenbolsa.fundamentales.price.data.loader.DataLoader;
import info.invertirenbolsa.fundamentales.price.data.loader.DataLoaderCsv;
import info.invertirenbolsa.fundamentales.price.impl.StockList;

import java.util.Map;

import org.apache.log4j.Logger;

public class AssetPortfolio {

    private static final Logger logger = Logger.getLogger(AssetPortfolio.class);
    
    private static final String DEFAULT_PATH = "C:\\Users\\00556998\\Downloads\\quantquote_daily_sp500_83986\\daily";
    
    //Given a group of 5 stocks it will calculate the correlation, volatility, performance, sharpe, and ponderation
    public static void main(String args[]) throws Exception {
        DataLoader loader = new DataLoaderCsv(DEFAULT_PATH);
        Map<String, StockList> data = loader.loadData();
        logger.info("Data loaded");
        
        
    }
}
