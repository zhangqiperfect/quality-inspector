package com.quality.inspector.common;

/**
 * @author ZQ
 * @create 2020-06-13
 */
public enum DefectEnum {
    /**
     * class=0
     */
    OPENCIRCUIT("虚焊"),
    /**
     * class=1
     */
    CRACK("隐裂");

    private DefectEnum(String defectType) {
        this.defectType = defectType;
    }

    /**
     * 缺陷类型
     */
    private final String defectType;

    public String getDefectType() {
        return defectType;
    }
}
