package com.quality.inspector.controller;

import com.alibaba.fastjson.JSON;
import com.quality.inspector.common.CodeEnum;
import com.quality.inspector.common.ResponseData;
import com.quality.inspector.common.ResultInfo;
import com.quality.inspector.service.ImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author hso
 * @data: 2020-5-20 14:58:39
 * @param:
 * @description: 图片处理
 */
@Api(value = "", tags = {"图片处理"})
@RequestMapping("/quality-inspector")
@RestController
@Slf4j
public class ImgController {
    @Autowired
    private ImgService imgService;

    @Value("${imageUrl}")
    private String imageUrl;

    @Value("${compressImageUrl}")
    private String compressImageUrl;
    /**
     * 单线程的线程池
     */
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @CrossOrigin
    @ApiOperation(value = "图片检测接口", notes = "检测图片接口")
    @PostMapping("/getCheckImg")
    public ResponseData getImg(@RequestBody String imgBase64) {
        ResultInfo imgDeal = imgService.getImgDeal(imageUrl,imgBase64);
//      ResultInfo imgDeal = imgService.getImgDeal("/home/sycv_wbk/deployment/image/", "20200611080134.jpg");
        if (imgDeal.isFlag()) {
            System.out.println(JSON.toJSONString(ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, imgDeal)));
            return ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, imgDeal);
        }
        return ResponseData.out(false, "算法处理失败", "", CodeEnum.SUCCESSS, imgDeal);
    }

    @CrossOrigin
    @ApiOperation(value = "触发视觉识别", notes = "触发视觉识别")
    @GetMapping("/trigger")
    public ResultInfo triggerRecognition() {

        //开启新的线程
        CompletableFuture.runAsync(() -> {
            imgService.AutoImgDeal();
        }, executorService);

        //同步返回
        ResultInfo resultInfo = new ResultInfo(true, "触发自动处理成功");
        return resultInfo;
    }
}
