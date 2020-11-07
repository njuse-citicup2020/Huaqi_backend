package com.example.Huaqi.bl;
import com.example.Huaqi.vo.ResponseVO;

public interface OptionService {

    /**
     * 根据标的物ETF_code 检索对应期权列表
     * @param code
     * @return
     */
    ResponseVO getOptionByETFCode(String code,String type);

    /**
     * 计算Delta曲线
     * @param code
     * @return
     */
    ResponseVO getDeltaCurve(String code);

    /**
     * 计算时间价值曲线
     * @param code
     * @return
     */
    ResponseVO getTimeValueCurve(String code);

    /**
     * 获得ETF对应的期权的交割时间
     * @param etfcode
     * @return
     */
    ResponseVO getOptionTradeDate(String etfcode);
}
