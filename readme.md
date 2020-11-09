# 更新日志

## 2020/11/9

+ data层增加了update方法

## 2020/11/7

+ 完成接口9~13，其中接口11需要等商院
+ 修改了sql文件，更新后的sql文件为citicup2.sql
+ 完成接口测试，但service层的具体逻辑没有测试

## 2020/11/6

+ 完成接口6~8，通过postman单接口测试
+ 修改了sql文件以及对应mapper等，注意原来的strategy更名为deal，更新后的sql文件为citicup2.sql

## 2020/10/28

新增了五个接口

1. 获取某时刻时间价值为负的所有期权，并按照时间价值从小到大排序

2. 获取某时刻某期权的卖一价、卖一量、买一价、买一量、行权价

3. 获取某时刻50ETF的卖一价、卖一量、买一价、买一量、行权价

4. 获取某时刻对冲期权

	***这个接口暂时不行，delta表还没出***

5. 获取某日50ETF最早的买一价

都在`data.StatisticRepo`下，详见代码文档注释

## 2020/10/27

* 迁移到新仓库
* 使用服务器的MySQL作为数据库

## 2020/10/25

* 给出逻辑实现需要的数据层接口

	* 当前ETF数据 `ETFMapper.currentETF`

	* 当前某个期权数据`OptionMapper.currentOption(optionCode)`

	* 某个月到期的期权

		```java
		interface OptionMapper{
			/**
		     * 获取某个月到期的期权List
		     * @param strike_month 到期月份，格式类似"2020-10"
		     * @return
		     */
		    List<OptionPO> getMonthOption(@Param("month") String strike_month);
		}
		```

	* 买卖记录

		* `StrategyMapper.addStrategy`
		* `TradeMapper.addTrade`
		
	* 获取当月时间价值最小的期权
	
		* 还没写，利用getMonthOption接口在Service层实现下吧

> 大多数表的字段都是NOT NULL的，所有添加的时候要set全属性

