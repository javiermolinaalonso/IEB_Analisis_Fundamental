package info.invertirenbolsa.fundamentales.price.impl;

import java.time.temporal.ChronoUnit;

public class CorrelationIntervalInputData {

    private final Integer interval;
    private final ChronoUnit intervalUnit;
    private final Integer step;
    private final ChronoUnit stepUnit;
    
    public CorrelationIntervalInputData(Integer interval, ChronoUnit intervalUnit, Integer step, ChronoUnit stepUnit) {
        super();
        this.interval = interval;
        this.intervalUnit = intervalUnit;
        this.step = step;
        this.stepUnit = stepUnit;
    }
    
    public Integer getInterval() {
        return this.interval;
    }
    public ChronoUnit getIntervalUnit() {
        return this.intervalUnit;
    }
    public Integer getStep() {
        return this.step;
    }
    public ChronoUnit getStepUnit() {
        return this.stepUnit;
    }
    
    
}
