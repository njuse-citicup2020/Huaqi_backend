package com.example.Huaqi.blImpl;

import com.example.Huaqi.bl.StrategyService;
import com.example.Huaqi.data.StrategyMapper;
import com.example.Huaqi.po.StrategyPO;
import com.example.Huaqi.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    StrategyMapper strategyMapper;

    int days;
    double profit;
    List<StrategyPO> strategyPOS;

    @Override
    public ResponseVO cumulativeRate(String startMonth, String endMonth) {
        int openingFund = 100000000;//商业组给定的期初基金，是定值
        CumulativeRateVO cumulativeRateVO = new CumulativeRateVO();
        List<StrategyPO> strategyPOS = strategyMapper.getStrategyInDateRank(startMonth+" 00:00:00", endMonth+" 23:59:59");
        int interval = strategyPOS.size()>30?strategyPOS.size()/30:1;//传给前端的数组大小不要超过30
        for(int i=0;i<strategyPOS.size();i+=interval){
            StrategyPO strategyPO = strategyPOS.get(i);
            cumulativeRateVO.addRate(strategyPO.getDate_time(),strategyPO.getModelProfit()/openingFund, strategyPO.getMarketProfit()/openingFund);
        }
        return ResponseVO.buildSuccess(cumulativeRateVO);
    }

    @Override
    public ResponseVO strategyInfo(String startMonth, String endMonth) {
        strategyPOS = strategyMapper.getStrategyInDateRank(startMonth+" 00:00:00", endMonth+" 23:59:59");
        //按date_time排序
        Collections.sort(strategyPOS, new Comparator<StrategyPO>() {
            @Override
            public int compare(StrategyPO s1, StrategyPO s2) {
                return s1.getDate_time().compareTo(s2.getDate_time());
            }
        });
        getDaysAndProfit(strategyPOS.get(0).getDate_time(),strategyPOS.get(strategyPOS.size()-1).getDate_time());
        //组装strategyInfoVO
        StrategyInfoVO strategyInfoVO = new StrategyInfoVO(days,profit);
        strategyInfoVO.setMaxProfit(maxProfit());
        strategyInfoVO.setMaxWithdrawal(maxWithdrawal());
        double mean = yieldMean();
        strategyInfoVO.setYieldMean(mean);
        strategyInfoVO.setYieldVar(yieldVar(mean));
        return ResponseVO.buildSuccess(strategyInfoVO);
    }

    @Override
    public ResponseVO varianceAndMeanOfTotalRate() {
        //TODO: 等商院给理财产品
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO strategyOverallInfo() {
        String start = strategyMapper.getMinDateTime().substring(0,10);
        String end = strategyMapper.getMaxDateTime().substring(0,10);
        getDaysAndProfit(start,end);
        StrategyOverallInfoVO strategyOverallInfoPO = new StrategyOverallInfoVO(days,profit);
        return ResponseVO.buildSuccess(strategyOverallInfoPO);
    }

    @Override
    public ResponseVO monthRank() {
        String start = strategyMapper.getMinDateTime();
        String end = strategyMapper.getMaxDateTime();
        MonthRankVO monthRankVO = new MonthRankVO(start,end);
        return ResponseVO.buildSuccess(monthRankVO);
    }

    private void getDaysAndProfit(String start, String end){
        //获取时间窗口
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            days = (int) ((sdf.parse(end).getTime() - sdf.parse(start).getTime() + 1) / (1000 * 60 * 60 * 24));
        }catch (Exception e){
            e.printStackTrace();
        }
        //组装StrategyOverallInfoVO
        double startProfit = strategyMapper.getModelProfitByDateTime(start+" 00:00:00").get(0);
        double endProfit = strategyMapper.getModelProfitByDateTime(end+" 00:00:00").get(0);
        profit = endProfit-startProfit;
    }

    private double maxProfit(){
        //维护三个指针,i,j记录两个端点的位置，k记录上一次最高点的位置
        int i=0,j=1,k=0;
        double res = 0;
        while(i < strategyPOS.size()){
            //如果后一个点比当前点更低，则指针后移一位
            while(i+1<strategyPOS.size() && strategyPOS.get(i+1).getModelProfit() <= strategyPOS.get(i).getModelProfit()){
                i++;
            }
            if(i+1>=strategyPOS.size()){
                break;
            }
            //找最高点
            j=i+1;
            if(k>=j){
                //如果上一次的最高点在j后面，则现在的最高点和之前相同，无需重新计算
            }else{//否则，往后遍历找最高点，
                k = j;
                while(j+1<strategyPOS.size()){
                    j++;
                    if(strategyPOS.get(j).getModelProfit() >= strategyPOS.get(k).getModelProfit()){
                        k = j;
                    }
                }
            }
            double tmp = strategyPOS.get(k).getModelProfit() - strategyPOS.get(i).getModelProfit();
            if(tmp>res){
                res = tmp;
            }
            i++;
        }
        return res;
    }

    private double maxWithdrawal(){
        //维护三个指针,i,j记录两个端点的位置，k记录上一次最低点的位置
        int i=0,j=1,k=0;
        double res = 0;
        while(i < strategyPOS.size()){
            //如果后一个点比当前点更高，则指针后移一位
            while(i+1<strategyPOS.size() && strategyPOS.get(i+1).getModelProfit() >= strategyPOS.get(i).getModelProfit()){
                i++;
            }
            if(i+1>=strategyPOS.size()){
                break;
            }
            //找最低点
            j=i+1;
            if(k>=j){
                //如果上一次的最低点在j后面，则现在的最低点和之前相同，无需重新计算
            }else{//否则，往后遍历找最低点，
                k = j;
                while(j+1<strategyPOS.size()){
                    j++;
                    if(strategyPOS.get(j).getModelProfit() <= strategyPOS.get(k).getModelProfit()){
                        k = j;
                    }
                }
            }
            double tmp = strategyPOS.get(k).getModelProfit() - strategyPOS.get(i).getModelProfit();
            if(tmp<res){
                res = tmp;
            }
            i++;
        }
        return res;
    }

    private double yieldMean(){
        double sum = 0;
        for(int i=0;i<strategyPOS.size();i++){
            sum += strategyPOS.get(i).getModelProfit();
        }
        return sum/strategyPOS.size();
    }

    private double yieldVar(double mean){
        double var = 0;
        for(int i=0;i<strategyPOS.size();i++){
            double diff = strategyPOS.get(i).getModelProfit() - mean;
            var += diff*diff;
        }
        var /= strategyPOS.size();
        return var;
    }
}
