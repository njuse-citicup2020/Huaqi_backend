package com.example.Huaqi.po;

public class ETFStatisticPO {
    private String objectDate; // 代表对象是某时刻的数据
    private Double ask1Price;   // 卖一价
    private Integer ask1Nums;
    private Double bid1Price; // 买一价
    private Integer bid1Nums;   // 买一数

    public String getObjectDate() {
        return objectDate;
    }

    public void setObjectDate(String objectDate) {
        this.objectDate = objectDate;
    }

    public Double getAsk1Price() {
        return ask1Price;
    }

    public void setAsk1Price(Double ask1Price) {
        this.ask1Price = ask1Price;
    }

    public Integer getAsk1Nums() {
        return ask1Nums;
    }

    public void setAsk1Nums(Integer ask1Nums) {
        this.ask1Nums = ask1Nums;
    }

    public Double getBid1Price() {
        return bid1Price;
    }

    public void setBid1Price(Double bid1Price) {
        this.bid1Price = bid1Price;
    }

    public Integer getBid1Nums() {
        return bid1Nums;
    }

    public void setBid1Nums(Integer bid1Nums) {
        this.bid1Nums = bid1Nums;
    }

    @Override
    public String toString() {
        return "ETFStatisticPO{" +
                "objectDate='" + objectDate + '\'' +
                ", ask1Price=" + ask1Price +
                ", ask1Nums=" + ask1Nums +
                ", bid1Price=" + bid1Price +
                ", bid1Nums=" + bid1Nums +
                '}';
    }
}
