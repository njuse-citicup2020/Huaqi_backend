package com.example.Huaqi.po;

/**
 * 策略类，包含了记录了对应日期时间的系统和大盘收益等策略信息
 */
public class StrategyPO {
    private Integer id;
    private String date_time;
    private double modelProfit;
    private double marketProfit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public double getModelProfit() {
        return modelProfit;
    }

    public void setModelProfit(double modelProfit) {
        this.modelProfit = modelProfit;
    }

    public double getMarketProfit() {
        return marketProfit;
    }

    public void setMarketProfit(double marketProfit) {
        this.marketProfit = marketProfit;
    }

    @Override
    public String toString() {
        return "StrategyPO{" +
                "id=" + id +
                ", date_time='" + date_time + '\'' +
                ", modelProfit=" + modelProfit +
                ", marketProfit=" + marketProfit +
                '}';
    }
}
