package com.example.Huaqi.data;

import com.example.Huaqi.po.ETFStatisticPO;
import com.example.Huaqi.po.OptionStatisticPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticRepoTest {
    @Autowired
    StatisticRepo statisticRepo;

    @Test
    public void testETF(){
        ETFStatisticPO etfStatisticPO = statisticRepo.getCurrentETFStatistic("2020-08-26 09:24:50");
        System.out.println(etfStatisticPO);
    }

    @Test
    public void testNegOptions(){
        List<OptionStatisticPO> list = statisticRepo.getNegTimeValueOptions("2020-03-25 14:57:43");
        for(OptionStatisticPO optionStatisticPO:list){
            System.out.println(optionStatisticPO);
        }
    }

    @Test
    public void getOption(){
        System.out.println(statisticRepo.getOptionStastic("2020-02-28 09:34:30","10002287.SH"));
    }

    @Test
    public void testGetETFBid1Earliest(){
        System.out.println(statisticRepo.getETFBid1Earliest("2020-02-28 09:34:30"));
        System.out.println(statisticRepo.getETFBid1Earliest("2020-03-25"));
    }
}
