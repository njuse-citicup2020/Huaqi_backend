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
    List<OptionStatisticPO> getOptionStatisticPerTenSeconds(@Param("table") String tableName, @Param("time") String timeStamp);

    ETFStatisticPO getETFStatisticPerTenSeconds(@Param("table") String tableName, @Param("time") String timeStamp);

    OptionStatisticPO getOptionByCodeAndTime(@Param("table") String tableName, @Param("time") String timeStamp,@Param("fileName")String fileName);

    Double getEarliestETFBid1(@Param("table") String tableName);

    OptionStatisticPO getHedgeOption(@Param("table") String tableName,@Param("file")String fileName);
}
