package com.example.Huaqi;

public class test {
    public static void main(String[] args) {
//        String name = "50ETF沽4月2.35@10002420.csv";
//        String name = "10002287.SH";
//        for(String s:name.split("\\.")){
//            System.out.println(s);
//        }

        String time = "2020-03-25 00:00:00";
        String timeStamp = time.substring(0,10);
        int month = Integer.parseInt(timeStamp.split("\\-")[1]);
        System.out.println(month);
    }
}
