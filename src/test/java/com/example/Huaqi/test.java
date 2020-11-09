package com.example.Huaqi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {

    private static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DAY_FORMAT = "yyyy-MM-dd";

    public static void main(String[] args) {
//        String name = "50ETF沽4月2.35@10002420.csv";
//        System.out.println('沽'==name.charAt(5));
//        String name = "10002287.SH";
//        for(String s:name.split("\\.")){
//            System.out.println(s);
//        }
//        String time = "2020-03-25 00:00:00";
//        String timeStamp = time.substring(0,10);
//        int month = Integer.parseInt(timeStamp.split("\\-")[1]);
//        System.out.println(month);
        System.out.println(test.isDateVail("2020-09-11 23:00:05",SECOND_FORMAT));
        System.out.println(test.isDateVail("2020-09-11 23:00:05",DAY_FORMAT));
        System.out.println(test.isDateVail("2020-09-11",DAY_FORMAT));
        System.out.println(test.isLegalDate("2020-09-11 23:00:05"));
    }

    public static boolean isDateVail(String date,String pattern) {
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
    }

    private static boolean isLegalDate(String sDate) {
        int legalLen = 10;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

}
