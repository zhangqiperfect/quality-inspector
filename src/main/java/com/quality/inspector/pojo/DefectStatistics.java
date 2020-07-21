package com.quality.inspector.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZQ
 * @create 2020-06-14
 */
@Data
public class DefectStatistics implements Serializable {
    /**
     * 累计缺陷统计
     */
    private int defectAll;
    /**
     * 检测总块数
     */
    private int checkCount;
    /**
     * 总的检测次数
     */
    private int checkTime;
    /**
     * 所有检测中出现缺陷的次数
     */
    private int defectTime;

    public DefectStatistics(int defectAll, int checkCount, int checkTime, int defectTime) {
        this.defectAll = defectAll;
        this.checkCount = checkCount;
        this.checkTime = checkTime;
        this.defectTime = defectTime;
    }
}
