package com.example.Huaqi.data;

import com.example.Huaqi.po.ETFStatisticPO;
import com.example.Huaqi.po.OptionStatisticPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticRepo {
    @Autowired
    private StatisticMapper statisticMapper;

    /**
     * 获取某时刻某期权的卖一价、卖一量、买一价、买一量、行权价
     *
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @return
     */
    public ETFStatisticPO getCurrentETFStatistic(String time) {
        String table = "daily_statistics@" + time.substring(0, 10);
        String timeStamp = time;
        return statisticMapper.getETFStatisticPerTenSeconds(table, timeStamp);
    }

    /**
     * 获取某时刻时间价值为负的所有期权，并按照时间价值从小到大排序
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @return
     */
    public List<OptionStatisticPO> getNegTimeValueOptions(String time) {
        String table = "daily_statistics@" + time.substring(0, 10);
        String timeStamp = time.substring(0, time.length() - 1) + "%";
        return statisticMapper.getOptionStatisticPerTenSeconds(table, timeStamp);
    }

    /**
     * 获取某时刻某期权的卖一价、卖一量、买一价、买一量、行权价
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @param optionCode xxxxxxx.SH
     * @return
     */
    public OptionStatisticPO getOptionStastic(String time,String optionCode){
        String table = "daily_statistics@" + time.substring(0, 10);
        String timeStamp = time.substring(0, time.length() - 1) + "%";
        String[] tmp = optionCode.split("\\.");
        String fileName = "%"+tmp[0]+"%";
        return statisticMapper.getOptionByCodeAndTime(table,timeStamp,fileName);
    }

    /**
     * 获取某日50ETF最早的买一价
     * @param time yyyy-MM-dd HH:mm:ss格式，或者yyyy-MM-dd，都可以
     * @return
     */
    public Double getETFBid1Earliest(String time){
        String timeStamp = time.substring(0,10);
        String table = "daily_statistics@"+timeStamp;
        return statisticMapper.getEarliestETFBid1(table);
    }

    /**
     * 获取对冲期权
     * 就是获取下个月到期的 delta最小的 认沽期权
     * TODO:【还没拿到Delta表的数据，这个接口暂时用不了】
     * @param time yyyy-MM-dd HH:mm:ss格式，或者yyyy-MM-dd，都可以
     * @return
     */
    public OptionStatisticPO getHedgeOption(String time){
        String timeStamp = time.substring(0,10);
        int month = Integer.parseInt(timeStamp.split("\\-")[1]);
        month=month==12?1:month+1;
        String fileName = "50ETF沽"+month+"%";
        return null;
    }
}
