package com.example.Huaqi.vo;

import java.util.List;

public class VarAndMeanOfTotalRateVO {
     List<String> marketName;// 理财产品名称列表，该数组应等于marketData长度
     List<List<Double>> modelData;//[[10,8]], [方差，均值]
     List<List<Double>> marketData;//[[10,8],...] //每一项为对应理财产品的[方差，均值]

    public VarAndMeanOfTotalRateVO() {
    }

    public List<String> getMarketName() {
        return marketName;
    }

    public void setMarketName(List<String> marketName) {
        this.marketName = marketName;
    }

    public List<List<Double>> getModelData() {
        return modelData;
    }

    public void setModelData(List<List<Double>> modelData) {
        this.modelData = modelData;
    }

    public List<List<Double>> getMarketData() {
        return marketData;
    }

    public void setMarketData(List<List<Double>> marketData) {
        this.marketData = marketData;
    }

    @Override
    public String toString() {
        return "VarAndMeanOfTotalRateVO{" +
                "marketName=" + marketName +
                ", modelData=" + modelData +
                ", marketData=" + marketData +
                '}';
    }
}
