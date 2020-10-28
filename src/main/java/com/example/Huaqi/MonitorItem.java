package com.example.Huaqi;

import com.example.Huaqi.po.OptionPO;

import java.time.LocalTime;

public class MonitorItem {

//    期权
    private OptionPO option;

//    期权量
    private int m;

//    对冲期权量
    private int n;

//    监测结束时间
    private LocalTime endTIme;

//    认购期权的行权价
    private double execPrice_call;

//    认购期权购买时的成交价
    private double price_call0;

//    认购期权购买时对冲期权的卖一价
    private double price_put0;

    public OptionPO getOption() {
        return option;
    }

    public void setOption(OptionPO option) {
        this.option = option;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public LocalTime getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(LocalTime endTIme) {
        this.endTIme = endTIme;
    }

    public double getExecPrice_call() {
        return execPrice_call;
    }

    public void setExecPrice_call(double execPrice_call) {
        this.execPrice_call = execPrice_call;
    }

    public double getPrice_call0() {
        return price_call0;
    }

    public void setPrice_call0(double price_call0) {
        this.price_call0 = price_call0;
    }

    public double getPrice_put0() {
        return price_put0;
    }

    public void setPrice_put0(double price_put0) {
        this.price_put0 = price_put0;
    }

    public MonitorItem() {
    }

    public MonitorItem(OptionPO option, int m, int n, LocalTime endTIme, double execPrice_call, double price_call0, double price_put0) {
        this.option = option;
        this.m = m;
        this.n = n;
        this.endTIme = endTIme;
        this.execPrice_call = execPrice_call;
        this.price_call0 = price_call0;
        this.price_put0 = price_put0;
    }
}
