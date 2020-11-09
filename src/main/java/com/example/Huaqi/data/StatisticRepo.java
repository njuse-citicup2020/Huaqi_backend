package com.example.Huaqi.data;

import com.example.Huaqi.po.ETFStatisticPO;
import com.example.Huaqi.po.OptionStatisticPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Repository
public class StatisticRepo {
    @Autowired
    private StatisticMapper statisticMapper;
    private static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DAY_FORMAT = "yyyy-MM-dd";

    /**
     * 获取某时刻ETF的卖一价、卖一量、买一价、买一量、行权价
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @return
     */
    public ETFStatisticPO getCurrentETFStatistic(String time) throws Exception {
        if(!isLegalDate(time,SECOND_FORMAT)){
            throw new Exception("Invalid time input，yyyy-MM-dd HH:mm:ss expected.");
        }
        String table = "daily_statistics@" + time.substring(0, 10);
        String timeStamp = time;
        return statisticMapper.getETFStatisticPerTenSeconds(table, timeStamp);
    }

    /**
     * 获取某时刻时间价值为负的所有期权，并按照时间价值从小到大排序
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @return
     */
    public List<OptionStatisticPO> getNegTimeValueOptions(String time) throws Exception {
        if(!isLegalDate(time,SECOND_FORMAT)){
            throw new Exception("Invalid time input，yyyy-MM-dd HH:mm:ss expected.");
        }
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
    public OptionStatisticPO getOptionStatistic(String time,String optionCode) throws Exception {
        if(!isLegalDate(time,SECOND_FORMAT)){
            throw new Exception("Invalid time input，yyyy-MM-dd HH:mm:ss expected.");
        }
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
    public Double getETFBid1Earliest(String time) throws Exception {
        if(!(isLegalDate(time,SECOND_FORMAT)||isLegalDate(time,DAY_FORMAT))){
            throw new Exception("Invalid time input，yyyy-MM-dd HH:mm:ss expected.");
        }
        String timeStamp = time.substring(0,10);
        String table = "daily_statistics@"+timeStamp;
        return statisticMapper.getEarliestETFBid1(table);
    }

    /**
     * 获取对冲期权
     * 就是获取下个月到期的 delta最小的 认沽期权
     * @param time yyyy-MM-dd HH:mm:ss格式，最后一位没用，10s为单位
     * @return
     */
    public OptionStatisticPO getHedgeOption(String time) throws Exception {
        if(!isLegalDate(time,SECOND_FORMAT)){
            throw new Exception("Invalid time input，yyyy-MM-dd HH:mm:ss expected.");
        }
        String timeStamp = time.substring(0,10);
        int month = Integer.parseInt(timeStamp.split("\\-")[1]);
        int year = Integer.parseInt(timeStamp.split("\\-")[0]);
        if(month==12){
            month=1;
            year+=1;
        }else{
            month+=1;
        }

        String timeQuery = month<10?year+"-0"+month+"%":year+"-"+month+"%";
        String lvlTable = "daily_lvl_data@"+timeStamp;
        String setTable = "daily_options_set@"+timeStamp;
        String code = statisticMapper.getHedgeOptionCode(lvlTable,setTable,timeQuery);
        System.out.println("hedgeOptionCode="+code);
        return getOptionStatistic(time,code);
    }

    /**
     * 判断输入是否合法
     * @param date
     * @param pattern
     * @return
     */
    private boolean isLegalDate(String date,String pattern) {
        if(pattern.equals(SECOND_FORMAT)) {
            //用于指定 日期/时间 模式
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            boolean flag = true;
            try {
                //Java 8 新添API 用于解析日期和时间
                LocalDateTime.parse(date, dtf);
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }else if(pattern.equals(DAY_FORMAT)){
            int legalLen = 10;
            if ((date == null) || (date.length() != legalLen)) {
                return false;
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = formatter.parse(date);
                return date.equals(formatter.format(d));
            } catch (Exception e) {
                return false;
            }
        }else {
            return false;
        }
    }
}
