package info.invertirenbolsa.fundamentales.price.data.loader;

import info.invertirenbolsa.fundamentales.price.impl.StockList;

import java.util.Map;

public interface DataLoader {

    Map<String, StockList> loadData();
    
    Map<String, StockList> loadData(Integer amount);
    
    StockList loadStockList(String ticker);
    
    void setDataFile(String dataFile);
}
