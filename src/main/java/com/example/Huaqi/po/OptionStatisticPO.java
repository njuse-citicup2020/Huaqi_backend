package com.example.Huaqi.po;

/**
 * 期权实时数据类，某时刻某期权的卖一价、卖一量、买一价、买一量、行权价
 */
public class OptionStatisticPO {
    private String optionDate; // 代表对象是某时刻的数据
    private Double ask1Price;   // 卖一价
    private Integer ask1Nums;
    private Double bid1Price; // 买一价
    private Integer bid1Nums;   // 买一数
    private String fileName; // 文件名，没用，但是我要用他来提取行权价和期权code
    private Double strikePrice; // 行权价
    private String optionCode; // 期权code，唯一标识符
    private Double timeValue; // 时间价值
    private String optionType; // Call or Put

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptionDate() {
        return optionDate;
    }

    public void setOptionDate(String optionDate) {
        this.optionDate = optionDate;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        // 装在fileName的时候提取出code和price
        String[] strings = fileName.split("月|@");
        String price = strings[1].substring(0,4);
        String code = strings[2].substring(0,8)+".SH";
        String type = fileName.charAt(5)=='沽'?"Put":"Call";
        setOptionType(type);
        setOptionCode(code);
        setStrikePrice(Double.valueOf(price));
        this.fileName = fileName;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public Double getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Double timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public String toString() {
        return "OptionStatisticPO{" +
                "optionDate='" + optionDate + '\'' +
                ", ask1Price=" + ask1Price +
                ", ask1Nums=" + ask1Nums +
                ", bid1Price=" + bid1Price +
                ", bid1Nums=" + bid1Nums +
                ", fileName='" + fileName + '\'' +
                ", strikePrice=" + strikePrice +
                ", optionCode='" + optionCode + '\'' +
                ", timeValue=" + timeValue +
                ", optionType='" + optionType + '\'' +
                '}';
    }
}
