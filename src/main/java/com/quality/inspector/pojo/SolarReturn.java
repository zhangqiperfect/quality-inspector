package com.quality.inspector.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZQ
 * @create 2020-06-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolarReturn implements Serializable {

    /**
     * 算法返回的状态，Fail表示有缺陷
     */
    private String status;
    /**
     * 检测后图片保存路径
     */
    private String save_path;
    /**
     * 算法保存图片名
     */
    private String image_name;
    /**
     * 检测总块数
     */
    private int detect_total;
    /**
     * 检测正常的块数
     */
    private int detect_normal;
    /**
     * 检测异常的块数
     */
    private int detect_defect;
    /**
     * 缺陷数组
     */
    private double[] defect_cls;
}
