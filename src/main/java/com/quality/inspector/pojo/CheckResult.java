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
@AllArgsConstructor
@NoArgsConstructor
public class CheckResult implements Serializable {
    /**
     * 检测总数
     */
    private Integer checkCount;
    /**
     * 检测正常的个数
     */
    private Integer normalCount;
    /**
     * 检测缺陷的个数
     */
    private Integer defectCount;
    /**
     * 检测是否通过
     */
    private boolean isPass;
}
