package com.example.Huaqi.data;

import com.example.Huaqi.enums.StrategyDealType;
import com.example.Huaqi.enums.StrategyStatus;
import com.example.Huaqi.po.DealPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealMapperTest {
    @Autowired
    DealMapper dealMapper;

    @Test
    public void test1(){
        DealPO dealPO = new DealPO();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        dealPO.setDealType(StrategyDealType.Sell_50ETF.getType());
        dealPO.setDealTime(ft.format(new Date()));
        dealPO.setItem("10002423.SH");
        dealPO.setPrice(500.);
        dealPO.setPricePer(200.);
        dealPO.setVolume(4.);
        dealPO.setTransferFee(300.);
        dealPO.setStatus(StrategyStatus.Success.getStatus());
        dealPO.setTradeId(2);
        dealMapper.addDeal(dealPO);
        System.out.println(dealMapper.getDealsByTradeId(2));
    }
}
