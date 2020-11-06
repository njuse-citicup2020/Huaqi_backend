package com.example.Huaqi.vo;

public class StrategyOverallInfoVO {
    double cumulativeYield;// 累计收益率
    double annualizedYield;// 年化收益率
    double basicYield;// 基础收益率
    double openingFund;// 期初基金
    double totalAssets;// 总资产

    public StrategyOverallInfoVO(int days, double profit) {
        openingFund = 100000000;//商业组给定
        basicYield = 0.001;//随便写的一个值，之后要商业组给定
        cumulativeYield = profit/openingFund;
        annualizedYield = cumulativeYield/days*365;
        totalAssets = profit + openingFund;
    }

    public double getCumulativeYield() {
        return cumulativeYield;
    }

    public void setCumulativeYield(double cumulativeYield) {
        this.cumulativeYield = cumulativeYield;
    }

    public double getAnnualizedYield() {
        return annualizedYield;
    }

    public void setAnnualizedYield(double annualizedYield) {
        this.annualizedYield = annualizedYield;
    }

    public double getBasicYield() {
        return basicYield;
    }

    public void setBasicYield(double basicYield) {
        this.basicYield = basicYield;
    }

    public double getOpeningFund() {
        return openingFund;
    }

    public void setOpeningFund(double openingFund) {
        this.openingFund = openingFund;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    @Override
    public String toString() {
        return "StrategyOverallInfoPO{" +
                "cumulativeYield=" + cumulativeYield +
                ", annualizedYield=" + annualizedYield +
                ", basicYield=" + basicYield +
                ", openingFund=" + openingFund +
                ", totalAssets=" + totalAssets +
                '}';
    }
}
