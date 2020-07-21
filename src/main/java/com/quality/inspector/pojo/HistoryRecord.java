package com.quality.inspector.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZQ
 * @create 2020-06-14
 */
@Data
public class HistoryRecord implements Serializable {
    /**
     * 图片名称
     */
    private String imageName;
    /**
     * 检测时间
     */
    private String systemTime;
    /**
     * 缺陷类型
     */
    private String defectType;
    /**
     * 加密图片
     */
    private String decodeImage;

    public HistoryRecord(String imageName, String systemTime, String defectType, String decodeImage) {
        this.imageName = imageName;
        this.systemTime = systemTime;
        this.defectType = defectType;
        this.decodeImage = decodeImage;
    }
}
