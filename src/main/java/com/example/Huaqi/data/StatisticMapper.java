package com.example.Huaqi.data;

import com.example.Huaqi.po.ETFStatisticPO;
import com.example.Huaqi.po.OptionStatisticPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
// 获取实时数据的Mapper
public interface StatisticMapper {
    //获取某时刻时间价值为负的所有期权，并按照时间价值从小到大排序
    List<OptionStatisticPO> getOptionStatisticPerTenSeconds(@Param("table") String tableName, @Param("time") String timeStamp);

    //获取某时刻50ETF
    ETFStatisticPO getETFStatisticPerTenSeconds(@Param("table") String tableName, @Param("time") String timeStamp);

    //获取某时刻某期权
    OptionStatisticPO getOptionByCodeAndTime(@Param("table") String tableName, @Param("time") String timeStamp,@Param("fileName")String fileName);

    //获取某日50ETF最早的买一价
    Double getEarliestETFBid1(@Param("table") String tableName);

    //获取某时刻对冲期权
    OptionStatisticPO getHedgeOption(@Param("table") String tableName,@Param("file")String fileName);

    /**
     获取对冲期权的code
     * @param lvlTableName lvl表名，如daily_lvl_data@2020-08-26
     * @param setTableName set表名，如daily_options_set@2020-08-26
     * @param timeStamp 时间，如2020-09%，精确到月份
     * @return
     */
    String getHedgeOptionCode(@Param("lvlTableName") String lvlTableName,@Param("setTableName") String setTableName, @Param("time") String timeStamp);
}
