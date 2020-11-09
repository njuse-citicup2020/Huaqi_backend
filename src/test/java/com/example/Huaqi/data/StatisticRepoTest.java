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
    public void testETF() {
        ETFStatisticPO etfStatisticPO = null;
        try {
            etfStatisticPO = statisticRepo.getCurrentETFStatistic("2020-08-26 09:24:50");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(etfStatisticPO);
    }

    @Test
    public void testNegOptions() {
        List<OptionStatisticPO> list = null;
        try {
            list = statisticRepo.getNegTimeValueOptions("2020-03-25 14:57:43");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (OptionStatisticPO optionStatisticPO : list) {
            System.out.println(optionStatisticPO);
        }
    }

    @Test
    public void getOption() {
        try {
            System.out.println(statisticRepo.getOptionStatistic("2020-02-28 09:34:30", "10002287.SH"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetETFBid1Earliest() {
        try {
            System.out.println(statisticRepo.getETFBid1Earliest("2020-02-28 09:34:30"));
            System.out.println(statisticRepo.getETFBid1Earliest("2020-03-25"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHedgeCode() {
        String time = "2020-08-26";
        try {
            statisticRepo.getHedgeOption(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHedgeOption() {
        String time = "2020-09-25 09:32:00";
        OptionStatisticPO optionStatisticPO = null;
        try {
            optionStatisticPO = statisticRepo.getHedgeOption(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(optionStatisticPO);
    }

}
