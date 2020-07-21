package com.quality.inspector.service;

import com.quality.inspector.common.ResultInfo;

/**
 * @author hso
 * @data: 2020/5/25 11:09
 * @param:
 * @description:
 */

public interface ImgService {
    /**
     * @param: imgUrl, imgName
     * @description: 自动获取算法处理图片
     */
    void AutoImgDeal();

    /**
     * @Description 手动检测图片
     * @Param imgurl 图片地址
     * @Return
     */
    ResultInfo getImgDeal(String imgurl ,String imgBase64);
}
