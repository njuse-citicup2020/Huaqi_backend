package com.example.Huaqi.vo;

import java.util.ArrayList;
import java.util.List;

public class CumulativeRateVO {
    List<String> date;
    List<Double> modelData;
    List<Double> marketData;

    public CumulativeRateVO(){
        date = new ArrayList<>();
        modelData = new ArrayList<>();
        marketData = new ArrayList<>();
    }

    public void addRate(String date_time, Double model, Double market){
        date.add(date_time);
        modelData.add(model);
        marketData.add(market);
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Double> getModelData() {
        return modelData;
    }

    public void setModelData(List<Double> modelData) {
        this.modelData = modelData;
    }

    public List<Double> getMarketData() {
        return marketData;
    }

    public void setMarketData(List<Double> marketData) {
        this.marketData = marketData;
    }

    @Override
    public String toString() {
        return "CumulativeRateVO{" +
                "date=" + date +
                ", modelData=" + modelData +
                ", marketData=" + marketData +
                '}';
    }
}
