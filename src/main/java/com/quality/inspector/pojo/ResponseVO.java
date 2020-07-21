package com.quality.inspector.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ZQ
 * @create 2020-06-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO implements Serializable {
    /**
     * 图片加密字符串
     */
    private Map<String, Object> imageBase64;
    /**
     * 检测结果
     */
    private CheckResult checkResult;
    /**
     * 缺陷历史
     */
    private List<HistoryRecord> historyRecord;
    /**
     * 缺陷统计
     */
    private DefectStatistics defectStatistics;
    /**
     * 车间
     */
    private String workshop;
    /**
     * 产线
     */
    private String productionLine;
    /**
     * 测点
     */
    private String measuringPoint;


}
