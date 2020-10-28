package com.example.Huaqi;

import com.example.Huaqi.po.OptionPO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class LogicFlow {

    private static double remainingFund;
    private static double remainingFund_i;
    private static double remainingFundLoop;
    private static double reserve;

    private static double B = -0.017;

    private static List<MonitorItem> monitorList;
    private static List<ExerciseItem> exerciseList;
    private static OptionPO hOption;
    private static double hOptionDelta;
    private static int hOptionReq;//待买入的对冲期权量
    private static int hOptionSize;//持有对冲期权量

    private static LocalTime curTime;
    private static LocalTime[] timeFrames = new LocalTime[]{
            LocalTime.of(9, 30, 0),
            LocalTime.of(10, 30, 0),
            LocalTime.of(11, 30, 0),
            LocalTime.of(13, 0, 0),
            LocalTime.of(14, 0, 0),
            LocalTime.of(15, 0, 0)
    };

    public static void main(String[] args) {
        for (LocalDate curMonth = LocalDate.of(2019, 5,1);
             curMonth.isBefore(LocalDate.of(2020, 10,1));
             curMonth = curMonth.plusMonths(1)){

//            T日
            remainingFund = 150000000;
            reserve = remainingFund * 0.02;
            exerciseList = new ArrayList<>();
            monitorList = new ArrayList<>();
            int accountETF = 0;
            for (int i = 0; i < 4; i ++){
                remainingFund_i = remainingFund / (4 - i);
                LocalTime startTime;
                LocalTime endTime;
                if (i < 2){
                    startTime = timeFrames[i];
                    endTime = timeFrames[i + 1];
                }
                else {
                    startTime = timeFrames[i + 1];
                    endTime = timeFrames[i + 2];
                }

                for (curTime = startTime; curTime.isBefore(endTime); curTime = curTime.plusSeconds(10)){
                    remainingFundLoop = 200000;
                    List<OptionPO> avaliableOptions = new ArrayList<>();//TODO 获取时间价值为负的期权列表，已排序
                    double ETFPrice = 0.0;//TODO 获取ETF卖一价
                    //TODO 计算对冲期权hOption和hOptionDelta
                    while (avaliableOptions.size() > 0){
                        OptionPO targetOption = avaliableOptions.get(0);
                        avaliableOptions.remove(0);
                        double targetTimeValue = targetOption.getTime_value();
                        if (targetTimeValue > B){
                            break;
                        }
                        if (targetOption.getCall_put().equals("put")){
                            int n = 0;
                            double price_put = 0.0;//TODO 目标期权的卖一价
                            n = Math.max(n, (int)(Math.min(remainingFund_i, remainingFundLoop) / (10000 * (price_put + ETFPrice))));
                            //TODO n = min(n, 市场上现有的targetOption期权卖一量, 市场上现有的ETF卖一量);
                            if (n == 0){
                                continue;
                            }

                            buy(n, price_put);
                            serviceCharge(n);

                            int k = n;
                            buy(k, ETFPrice);
                            serviceCharge(k);
                            accountETF += k;

                            double execPrice_put = 0.0;//TODO 目标期权的行权价
                            exerciseList.add(new ExerciseItem(targetOption, execPrice_put, n));
                        }
                        else {
                            int n = 0;
                            double price_call = 0.0;//TODO 目标期权的卖一价
                            double execPrice_call = 0.0;//TODO 目标期权的行权价
                            double price_put = 0.0;//TODO 对冲期权的卖一价
                            n = Math.max(n, (int)(Math.min(remainingFund_i, remainingFundLoop) / (10000 * (price_put - hOptionDelta * (price_call + execPrice_call)))));
                            //TODO n = min(n, 市场上现有的hOption期权卖一量, 市场上现有的targetOption期权卖一量 / (-hOptionDelta));
                            if (n == 0){
                                continue;
                            }

                            int m = (int)(-hOptionDelta * n);
                            buy(m, price_call + execPrice_call);
                            buy(n, price_put);
                            serviceCharge(m);
                            if (curTime.isBefore(LocalTime.of(14, 50, 0))){
                                if (curTime.plusMinutes(10).isBefore(LocalTime.of(14, 50, 0))){
                                    monitorList.add(new MonitorItem(targetOption, m, n, curTime.plusMinutes(10), execPrice_call, price_call, price_put));
                                }
                                else {
                                    monitorList.add(new MonitorItem(targetOption, m, n, LocalTime.of(14, 50, 0), execPrice_call, price_call, price_put));
                                }
                            }
                            else {
                                monitorList.add(new MonitorItem(targetOption, m, n, curTime, execPrice_call, price_call, price_put));
                            }

                            reserve += 10000 * n * price_put;
                        }
                    }
                    monitor();
                    buyHOption();
                }
            }

//            T+1日
            for (ExerciseItem item : exerciseList){
                if (item.getOption().getCall_put().equals("call")){
                    remainingFund -= 10000 * item.getExecPrice() * item.getSize();
                    accountETF += item.getSize();
                }
                else {
                    remainingFund += 10000 * item.getExecPrice() * item.getSize();
                    accountETF -= item.getSize();
                }
                serviceCharge(item.getSize());
            }

//            T+2日
            double ETFPrice = 0.0;//TODO T+2日最早的ETF价格
            remainingFund += 10000 * accountETF * ETFPrice;
            serviceCharge(accountETF);
            double price_put = 0.0;//TODO T+2日最早的对冲期权买一价
            remainingFund += 10000 * hOptionSize * price_put;
            serviceCharge(hOptionSize);
        }
    }

    private static void monitor(){
        double ETFPrice = 0.0;//TODO ETF卖一价
        for (MonitorItem item : monitorList){
            if (item.getEndTIme().isAfter(curTime)){
                double price_call = 0.0;//TODO item.option买一价
                double time_value = item.getPrice_call0() - Math.max(0, ETFPrice - item.getExecPrice_call());
                if ((time_value < B && price_call / item.getPrice_call0() > 1.05)
                        || (time_value >= B && price_call / item.getPrice_call0() > 1.03)){
                    sell(item.getM(), price_call + item.getExecPrice_call());
                    sell(item.getN(), item.getPrice_put0());
                    serviceCharge(item.getM());
                    monitorList.remove(item);
                    reserve -= 10000 * item.getN() * item.getPrice_put0();
                }
                else if (time_value >= B && price_call / item.getPrice_call0() <= 1.03){
                    hOptionSize += item.getN();
                    hOptionReq += item.getN();
                    serviceCharge(item.getN());
                    exerciseList.add(new ExerciseItem(item.getOption(), item.getExecPrice_call(), item.getM()));
                    monitorList.remove(item);
                }
            }
            else {
                hOptionSize += item.getN();
                hOptionReq += item.getN();
                serviceCharge(item.getN());
                exerciseList.add(new ExerciseItem(item.getOption(), item.getExecPrice_call(), item.getM()));
                monitorList.remove(item);
            }
        }
    }

    private static void buyHOption(){
        double price_put = 0.0;//TODO 对冲期权的卖一价
        int size = 0;//TODO Math.min(hOptionReq, 对冲期权的卖一量)
        reserve -= 10000 * size * price_put;
        hOptionReq -= size;
    }

    private static void buy(int n, double price){
        remainingFundLoop -= 10000 * n * price;
        remainingFund_i -= 10000 * n * price;
        remainingFund -= 10000 * n * price;
    }

    private static void sell(int n, double price){
        remainingFundLoop += 10000 * n * price;
        remainingFund_i += 10000 * n * price;
        remainingFund += 10000 * n * price;
    }

    private static void serviceCharge(int n){
        remainingFundLoop -= 1.7 * n;
        remainingFund_i -= 1.7 * n;
        remainingFund -= 1.7 * n;
    }
}
