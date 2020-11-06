package com.example.Huaqi.data;

import com.example.Huaqi.po.StrategyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StrategyMapper {
    int addStrategy(StrategyPO strategyPO);
    String getMinDateTime();
    String getMaxDateTime();
    List<StrategyPO> getStrategyInDateRank(@Param("start_time") String startTime, @Param("end_time") String endTime);
    List<Double> getModelProfitByDateTime(@Param("date_time") String dateTime);
    List<StrategyPO> getAllStrategy();
}
