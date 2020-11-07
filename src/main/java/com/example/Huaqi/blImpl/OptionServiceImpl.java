package com.example.Huaqi.blImpl;

import com.example.Huaqi.bl.OptionService;
import com.example.Huaqi.data.OptionMapper;
import com.example.Huaqi.po.OptionPO;
import com.example.Huaqi.vo.*;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Date: 2020-09-10
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    OptionMapper optionMapper;

    @Override
    public ResponseVO getOptionByETFCode(String code,String type) {
        List<OptionPO> options;
        if("call".equals(type)){
            options = optionMapper.getCallOptionByETF(code);
        }else if("put".equals(type)){
            options = optionMapper.getPutOptionByETF(code);
        }else {
            return ResponseVO.buildFailure("TypeError");
        }

        if(options.size()==0) {
            return ResponseVO.buildFailure("No Option Found");
        }else {
            return ResponseVO.buildSuccess(options);
        }
    }

    @Override
    public ResponseVO getDeltaCurve(String code) {
        List<OptionPO> options = optionMapper.getOptionByETF(code);
        if(options.size()==0) {
            return ResponseVO.buildFailure("No Option Found");
        }else {
            // create curve
            HashMap<String,Object> res = new HashMap<>();
            // 认购
            PolylinesVO call_data = new PolylinesVO();
            PolylinesVO put_data = new PolylinesVO();

            HashMap<String,HashMap<Double,Double>> call = new HashMap<>();
            HashMap<String,HashMap<Double,Double>> put = new HashMap<>();
            // key:month value:<price,delta>
            // load to 3-d map
            for(OptionPO option:options){
                String month = option.getLast_tradedate().substring(0,10).replace("-","");
                HashMap<String,HashMap<Double,Double>> target;
                HashMap<Double,Double> map;
                if(option.getCall_put().equals("认购"))
                    target = call;
                else
                    target = put;
                if(target.containsKey(month))
                    map = target.get(month);
                else
                    map = new HashMap<>();
                map.put(option.getStrike_price(),option.getDelta());
                target.put(month,map);
            }
            ArrayList<String> date = new ArrayList<>(Arrays.asList(put.keySet().toArray(new String[0])));
            ArrayList<Double> prices = null;
            // get price array
            for(String d:date){
                HashMap<Double,Double> call_tmp = call.get(d);
                ArrayList<Double> tmp = new ArrayList<>(Arrays.asList(call_tmp.keySet().toArray(new Double[0])));
                if(prices==null){
                    prices = tmp;
                }else {
                    prices.retainAll(tmp);
                }
            }
            Collections.sort(prices);
            ArrayList<ArrayList<Double>> call_ys = new ArrayList<>();
            ArrayList<ArrayList<Double>> put_ys = new ArrayList<>();
            for(String d:date){
                HashMap<Double,Double> call_tmp = call.get(d);
                HashMap<Double,Double> put_tmp = call.get(d);
                ArrayList<Double> call_y = new ArrayList<>();
                ArrayList<Double> put_y = new ArrayList<>();
                for(Double price:prices){
                    call_y.add(call_tmp.get(price));
                    put_y.add(put_tmp.get(price));
                }
                call_ys.add(call_y);
                put_ys.add(put_y);
            }
            call_data.setY_axis(call_ys);
            call_data.setX_axis(prices);
            call_data.setDate(date);
            put_data.setY_axis(put_ys);
            put_data.setX_axis(prices);
            put_data.setDate(date);
            res.put("call",call_data);
            res.put("put",put_data);
            return ResponseVO.buildSuccess(res);
        }
    }

    @Override
    public ResponseVO getTimeValueCurve(String code) {
        List<OptionPO> options = optionMapper.getOptionByETF(code);
        if(options.size()==0) {
            return ResponseVO.buildFailure("No Option Found");
        }else {
            // create curve
            HashMap<String,Object> res = new HashMap<>();
            // 认购
            PolylinesVO call_data = new PolylinesVO();
            PolylinesVO put_data = new PolylinesVO();

            HashMap<String,HashMap<Double,Double>> call = new HashMap<>();
            HashMap<String,HashMap<Double,Double>> put = new HashMap<>();
            // key:month value:<price,delta>
            // load to 3-d map
            for(OptionPO option:options){
                String month = option.getLast_tradedate().substring(0,10).replace("-","");
                HashMap<String,HashMap<Double,Double>> target;
                HashMap<Double,Double> map;
                if(option.getCall_put().equals("认购"))
                    target = call;
                else
                    target = put;
                if(target.containsKey(month))
                    map = target.get(month);
                else
                    map = new HashMap<>();
                map.put(option.getStrike_price(),option.getTime_value());
                target.put(month,map);
            }
            ArrayList<String> date = new ArrayList<>(Arrays.asList(put.keySet().toArray(new String[0])));
            ArrayList<Double> prices = null;
            // get price array
            for(String d:date){
                HashMap<Double,Double> call_tmp = call.get(d);
                ArrayList<Double> tmp = new ArrayList<>(Arrays.asList(call_tmp.keySet().toArray(new Double[0])));
                if(prices==null){
                    prices = tmp;
                }else {
                    prices.retainAll(tmp);
                }
            }
            Collections.sort(prices);
            ArrayList<ArrayList<Double>> call_ys = new ArrayList<>();
            ArrayList<ArrayList<Double>> put_ys = new ArrayList<>();
            for(String d:date){
                HashMap<Double,Double> call_tmp = call.get(d);
                HashMap<Double,Double> put_tmp = call.get(d);
                ArrayList<Double> call_y = new ArrayList<>();
                ArrayList<Double> put_y = new ArrayList<>();
                for(Double price:prices){
                    call_y.add(call_tmp.get(price));
                    put_y.add(put_tmp.get(price));
                }
                call_ys.add(call_y);
                put_ys.add(put_y);
            }
            call_data.setY_axis(call_ys);
            call_data.setX_axis(prices);
            call_data.setDate(date);
            put_data.setY_axis(put_ys);
            put_data.setX_axis(prices);
            put_data.setDate(date);
            res.put("call",call_data);
            res.put("put",put_data);
            return ResponseVO.buildSuccess(res);
        }
    }

    @Override
    public ResponseVO getOptionTradeDate(String etfcode) {
        List<String> tradeDate = optionMapper.getTradeDate(etfcode);
        if(tradeDate.size()==0) {
            return ResponseVO.buildFailure("No Option Found");
        }else {
            List<String> res = new ArrayList<>();
            tradeDate.forEach(i->res.add(i.substring(0,10).replace("-","")));
            return ResponseVO.buildSuccess(res);
        }
    }
}
