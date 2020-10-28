package com.example.Huaqi.data;

import com.example.Huaqi.po.OptionStatisticPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StatisticMapperTest {
    @Autowired
    StatisticMapper statisticMapper;

    @Test
    public void testGetOptionStatisticPerTenSeconds(){

        long startTime = System.currentTimeMillis();
        String table = "daily_statistics@2020-08-26";
        String time = "2020-08-26 09:24:5%";
        List<OptionStatisticPO> optionStatisticPOS = statisticMapper.getOptionStatisticPerTenSeconds(table,time);
        for (OptionStatisticPO optionStatistic:optionStatisticPOS){
            System.out.println(optionStatistic);
        }

        long endTime = System.currentTimeMillis();    //获取结束时间\
        System.out.println("查询时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }

    @Test
    public void testGetOptionStatisticPerTenSecondsError(){
        String table = "daily_statistics@2020-08-32";
        String time = "2020-08-26 09:24:5%";
        List<OptionStatisticPO> optionStatisticPOS;
        try {
            optionStatisticPOS= statisticMapper.getOptionStatisticPerTenSeconds(table, time);
            for (OptionStatisticPO optionStatistic:optionStatisticPOS){
                System.out.println(optionStatistic);
            }
        }catch (Exception e){
            System.out.println("-------");
            System.out.println(e.getMessage());
            System.out.println("-------");
            e.printStackTrace();
        }
    }
}
