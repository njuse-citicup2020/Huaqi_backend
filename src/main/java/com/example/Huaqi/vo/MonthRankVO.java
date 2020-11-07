package com.example.Huaqi.vo;

import java.util.ArrayList;
import java.util.List;

public class MonthRankVO {
    List<String> interval;

    public MonthRankVO(String start, String end){
        interval = new ArrayList<>(2);
        interval.add(start.substring(0,10));
        interval.add(end.substring(0,10));
    }

    public List<String> getInterval() {
        return interval;
    }

    public void setInterval(List<String> interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "MonthRankVO{" +
                "interval=" + interval +
                '}';
    }
}
