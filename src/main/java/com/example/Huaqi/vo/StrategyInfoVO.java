package com.example.Huaqi.vo;

public class StrategyInfoVO {
    double cumulativeProfit;// 累计盈亏
    double cumulativeYield;// 累计收益率
    double annualizedYield;// 年化收益率
    double maxProfit;// 最大收益
    double maxWithdrawal;// 最大回撤
    double yieldVar;// 收益率方差
    double yieldMean;// 平均收益率

    public StrategyInfoVO(int days, double profit) {
        cumulativeProfit = profit;
        cumulativeYield = profit/100000000;
        annualizedYield = cumulativeYield/days*365;
    }

    public double getCumulativeProfit() {
        return cumulativeProfit;
    }

    public void setCumulativeProfit(double cumulativeProfit) {
        this.cumulativeProfit = cumulativeProfit;
    }

    public double getCumulativeYield() {
        return cumulativeYield;
    }

    public double getAnnualizedYield() {
        return annualizedYield;
    }

    public void setAnnualizedYield(double annualizedYield) {
        this.annualizedYield = annualizedYield;
    }

    public void setCumulativeYield(double cumulativeYield) {
        this.cumulativeYield = cumulativeYield;
    }

    public double getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(double maxProfit) {
        this.maxProfit = maxProfit;
    }

    public double getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(double maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }

    public double getYieldVar() {
        return yieldVar;
    }

    public void setYieldVar(double yieldVar) {
        this.yieldVar = yieldVar;
    }

    public double getYieldMean() {
        return yieldMean;
    }

    public void setYieldMean(double yieldMean) {
        this.yieldMean = yieldMean;
    }

    @Override
    public String toString() {
        return "StrategyInfoPO{" +
                "cumulativeProfit=" + cumulativeProfit +
                ", cumulativeYield=" + cumulativeYield +
                ", annualizedYield=" + annualizedYield +
                ", maxProfit=" + maxProfit +
                ", maxWithdrawal=" + maxWithdrawal +
                ", yieldVar=" + yieldVar +
                ", yieldMean=" + yieldMean +
                '}';
    }
}
