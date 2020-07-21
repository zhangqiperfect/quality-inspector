package com.quality.inspector.service.serviceImpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import com.quality.inspector.common.CodeEnum;
import com.quality.inspector.common.DefectEnum;
import com.quality.inspector.common.ResponseData;
import com.quality.inspector.config.WebSocket;
import com.quality.inspector.pojo.*;
import com.quality.inspector.common.ResultInfo;
import com.quality.inspector.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {
    /**
     * 累计检测太阳能电路板总块数
     */
    private static AtomicInteger checkCount = new AtomicInteger(0);
    /**
     * 累计缺陷总数
     */
    private static AtomicInteger defectALl = new AtomicInteger(0);
    /**
     * 总检测次数
     */
    private static AtomicInteger checkTime = new AtomicInteger(0);
    /**
     * 检测中出现问题的次数
     */
    private static AtomicInteger defectTime = new AtomicInteger(0);

    /**
     * 文件夹中的文件个数
     */
    private static AtomicInteger fileNum = new AtomicInteger(0);
    /**
     * 文件夹中的文件夹个数
     */
    private static AtomicInteger DirectoryCount = new AtomicInteger(0);
    /**
     * 有界队列
     */
    private static LimitQueue<HistoryRecord> LimitQueue = new LimitQueue<HistoryRecord>(3);

    @Value("${imageDealUrl}")
    String imageDealUrl;

    @Value("${imageUrl}")
    String imageUrl;
    @Autowired
    WebSocket webSocket;

    //自动获取
    @Override
    public void AutoImgDeal() {
        System.out.println("autoimgdeal被调用");
//        //手动转换为自动时需先清除手动数据
        checkCount.set(0);
        defectALl.set(0);
        checkTime.set(0);
        defectTime.set(0);
        fileNum.set(0);
        DirectoryCount.set(0);
        LimitQueue.clear();
        //遍历文件夹获取图片名称和地址
        File file = new File(imageUrl);
        if (file.exists()) {
            log.info("文件夹是否存在：{}", file.exists());
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(f -> {
                if (f.isDirectory()) {
                    DirectoryCount.getAndIncrement();
                } else {
                    String img_name = f.getName();
                    String absolutePath = f.getAbsolutePath();
                    String imgUrl = absolutePath.replace(img_name, "");
                    fileNum.getAndIncrement();
                    System.out.println(checkTime.get());
                    log.info("图片名称：{} ,图片地址：{}", img_name, imageUrl);
                    ResultInfo resultInfo = getResultInfo(imageUrl, img_name);
                    if (resultInfo != null) {
                        if (resultInfo.isFlag()) {
                            log.info("算法处理结果：{}", resultInfo.getData());
                            webSocket.sendMessage((JSONObject) JSONObject.toJSON(ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, resultInfo)));
                        }
                    }

                }
            });
        }
    }
//        long s = System.currentTimeMillis();
//        for (int i = 1; i <= 20; i++) {
//            checkCount.getAndAdd(72);
//            checkTime.getAndIncrement();
//            defectTime.getAndIncrement();
//            if (i % 3 == 0) {
//                defectALl.getAndAdd(1);
//                defectALl.getAndAdd(1);
//                ResponseVO responseVO = new ResponseVO();
//                responseVO.setWorkshop("3");
//                responseVO.setProductionLine("A");
//                responseVO.setMeasuringPoint("层前");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM:hh:ss yyyy-MM-dd");
//                HistoryRecord historyRecord1 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.CRACK.getDefectType(), "E:/桌面");
//                List<HistoryRecord> historyRecords = new ArrayList<>();
//                historyRecords.add(historyRecord1);
//                LimitQueue.offer(historyRecord1);
//                responseVO.setHistoryRecord(new ArrayList<>(LimitQueue));
//                responseVO.setCheckResult(new CheckResult(72, 71, 1, false));
//                responseVO.setDefectStatistics(new DefectStatistics(defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get()));
////            Map<String, Object> decodeImage = getDecodeImage("E:/桌面/image/1.jpg");
//                Map<String, Object> decodeImage = compressAndDecode("E:/桌面/image/1.jpg");
//                responseVO.setImageBase64(decodeImage);
//                ResultInfo resultInfo = new ResultInfo(true, responseVO);
//                webSocket.sendMessage((JSONObject) JSONObject.toJSON(ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, resultInfo)));
//
//            } else if (i % 3 == 1) {
//                defectALl.getAndAdd(2);
//                defectALl.getAndAdd(2);
//                ResponseVO responseVO = new ResponseVO();
//                responseVO.setWorkshop("3");
//                responseVO.setProductionLine("A");
//                responseVO.setMeasuringPoint("层前");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM:hh:ss yyyy-MM-dd");
//                HistoryRecord historyRecord1 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.CRACK.getDefectType(), "E:/桌面");
//                HistoryRecord historyRecord2 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.OPENCIRCUIT.getDefectType(), "E:/桌面");
//                List<HistoryRecord> historyRecords = new ArrayList<>();
//                historyRecords.add(historyRecord1);
//                historyRecords.add(historyRecord2);
//                LimitQueue.offer(historyRecord1);
//                LimitQueue.offer(historyRecord2);
//                responseVO.setHistoryRecord(new ArrayList<>(LimitQueue));
//                responseVO.setCheckResult(new CheckResult(72, 70, 2, false));
//                responseVO.setDefectStatistics(new DefectStatistics(defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get()));
////            Map<String, Object> decodeImage = getDecodeImage("E:/桌面/image/1.jpg");
//                Map<String, Object> decodeImage = compressAndDecode("E:/桌面/image/1.jpg");
//                responseVO.setImageBase64(decodeImage);
//                ResultInfo resultInfo = new ResultInfo(true, responseVO);
//                webSocket.sendMessage((JSONObject) JSONObject.toJSON(ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, resultInfo)));
//            } else {
//                defectALl.getAndAdd(3);
//                defectALl.getAndAdd(3);
//                ResponseVO responseVO = new ResponseVO();
//                responseVO.setWorkshop("3");
//                responseVO.setProductionLine("A");
//                responseVO.setMeasuringPoint("层前");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM:hh:ss yyyy-MM-dd");
//                HistoryRecord historyRecord1 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.CRACK.getDefectType(), "E:/桌面");
//                HistoryRecord historyRecord2 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.OPENCIRCUIT.getDefectType(), "E:/桌面");
//                HistoryRecord historyRecord3 = new HistoryRecord(new Date().getTime() + ".jpg", dateFormat.format(new Date()), DefectEnum.CRACK.getDefectType(), "E:/桌面");
//                List<HistoryRecord> historyRecords = new ArrayList<>();
//                historyRecords.add(historyRecord1);
//                historyRecords.add(historyRecord2);
//                historyRecords.add(historyRecord3);
//                LimitQueue.offer(historyRecord1);
//                LimitQueue.offer(historyRecord2);
//                LimitQueue.offer(historyRecord3);
//                responseVO.setHistoryRecord(new ArrayList<>(LimitQueue));
//                responseVO.setCheckResult(new CheckResult(72, 69, 3, false));
//                responseVO.setDefectStatistics(new DefectStatistics(defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get()));
////            Map<String, Object> decodeImage = getDecodeImage("E:/桌面/image/1.jpg");
//                Map<String, Object> decodeImage = compressAndDecode("E:/桌面/image/1.jpg");
//                responseVO.setImageBase64(decodeImage);
//                ResultInfo resultInfo = new ResultInfo(true, responseVO);
//                webSocket.sendMessage((JSONObject) JSONObject.toJSON(ResponseData.out(true, "算法处理成功", "", CodeEnum.SUCCESSS, resultInfo)));
//            }
//
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("总耗时={}" + (e - s));


    //手动获取
    @Override
    public ResultInfo getImgDeal(String imgurl, String imgBase64) {
        //如果图像数据为空
        if (org.apache.commons.lang3.StringUtils.isEmpty(imgBase64)) {
            return new ResultInfo(false, "传入图片加密字符串为空");
//            return ResponseData.out(false, "传入图片加密字符串为空", null, CodeEnum.ERROR, null);
        }
        //去除json的首尾不符合解码的字符串
        String replace = imgBase64.replace("{\"imgBase64\":\"data:image/jpeg;base64,", "");
        String newStr = replace.replace("\"}", "");
        BASE64Decoder decoder = new BASE64Decoder();
        long start = System.currentTimeMillis();
        String imgName = null;
        OutputStream out = null;
        try {
            //解密
            byte[] b = decoder.decodeBuffer(newStr);
            //处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //图片名称
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            imgName = date + ".jpg";
            System.out.println("imageFullName=" + imgName);
            File image = new File(imageUrl + imgName);
            if (!image.exists()) {
                image.getParentFile().mkdir();
            }
            out = new FileOutputStream(imageUrl + imgName);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("图片存储异常");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //计数器归零，队列清空
        checkCount.set(0);
        defectALl.set(0);
        checkTime.set(0);
        defectTime.set(0);
        LimitQueue.clear();
        ResultInfo resultInfo = getResultInfo(imgurl, imgName);
        return resultInfo;
    }

    //获取算法处理结果
    private ResultInfo getResultInfo(String image_path, String image_name) {
        //调用算法获取处理结果
        JSONObject jsonData = new JSONObject();
        jsonData.put("task_id", "task_id");
        jsonData.put("task_type", "Solar_Detection_Tf");
        jsonData.put("callback", "");
        JSONObject contentData = new JSONObject();
        contentData.put("image_path", image_path);
        contentData.put("image_name", image_name);

        jsonData.put("content", contentData);
        jsonData.put("timestamp", "time.time");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> httpEntityImage = new HttpEntity<>(jsonData, null);
        //若图片已经识别完毕重新进行识别
        ResponseEntity<String> imgDealResult = null;
        try {
            //算法开始时间
            long solar_start = System.currentTimeMillis();
            //请求算法接口处理图片
            imgDealResult = restTemplate
                    .exchange(imageDealUrl, HttpMethod.POST, httpEntityImage, String.class);
            //算法处理完成时间
            long solar_end = System.currentTimeMillis();
            log.info("算法耗时：{}", (solar_end - solar_start));
        } catch (Exception e) {
            log.error("获取算法处理异常");
            return new ResultInfo(false, "获取算法处理异常");
        }
        if (imgDealResult != null) {
            ResponseVO responseVO = new ResponseVO();
            String body = imgDealResult.getBody();
            if (StringUtils.isEmpty(body)) {
                return new ResultInfo(false, "获取算法处理异常");
            }
            System.out.println(body);

            SolarReturn solarReturn = null;
            try {
                solarReturn = JSON.parseObject(body, SolarReturn.class);
            } catch (Exception e) {
                log.info("json解析异常");
                return new ResultInfo(false, "json解析异常");
            }
            if (solarReturn == null) {
                return new ResultInfo(false, "json解析异常");
            }
            String status = solarReturn.getStatus();
            String imgName = solarReturn.getImage_name();
            String save_path = solarReturn.getSave_path();
            int detect_total = solarReturn.getDetect_total();
            int detect_normal = solarReturn.getDetect_normal();
            int detect_defect = solarReturn.getDetect_defect();
            double[] defectTypes = solarReturn.getDefect_cls();

            log.info("算法处理状态status：{}", status);
            //检测次数加1
            checkTime.getAndIncrement();
            //检测块数累加
            checkCount.getAndAdd(detect_total);

            //检测到有缺陷
            if (status.equals("Fail")) {
                //出现缺陷的检测次数加1
                defectTime.getAndIncrement();
                List<HistoryRecord> historyRecords = new ArrayList<>();
                Arrays.stream(defectTypes).forEach(defect -> {
                    if (defect == 0.0) {
                        //累计缺陷次数加1
                        defectALl.getAndIncrement();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss yyy-MM-dd");
//                            String imageName, String systemTime, String defectType, String decodeImage
                        HistoryRecord historyRecord = new HistoryRecord(imgName, dateFormat.format(new Date()), DefectEnum.CRACK.getDefectType(), save_path);
                        LimitQueue.offer(historyRecord);
                        historyRecords.add(historyRecord);
                    } else {
                        //累计缺陷次数加1
                        defectALl.getAndIncrement();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss yyy-MM-dd");
                        HistoryRecord historyRecord = new HistoryRecord(imgName, dateFormat.format(new Date()), DefectEnum.OPENCIRCUIT.getDefectType(), save_path);
                        LimitQueue.offer(historyRecord);
                        historyRecords.add(historyRecord);
                    }
                });
                //封装历史缺陷
//              responseVO.setHistoryRecord(historyRecords);
                responseVO.setHistoryRecord(new ArrayList<>(LimitQueue));
                responseVO.setCheckResult(new CheckResult(detect_total, detect_normal, detect_defect, false));
                //缺陷统计
                log.info("缺陷总数={}，检测总数={}，检测次数={}，出现缺陷的次数={}", defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get());
                responseVO.setDefectStatistics(new DefectStatistics(defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get()));

            } else {
                //检测全部通过，无缺陷
                responseVO.setHistoryRecord(new ArrayList<>(LimitQueue));
                responseVO.setCheckResult(new CheckResult(detect_total, detect_normal, detect_defect, true));
                log.info("缺陷总数={}，检测总数={}，检测次数={}，出现缺陷的次数={}", defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get());
                responseVO.setDefectStatistics(new DefectStatistics(defectALl.get(), checkCount.get(), checkTime.get(), defectTime.get()));
            }
            //硬编码"车间","产线","测点"，暂时不处理
            responseVO.setWorkshop("3");
            responseVO.setProductionLine("A");
            responseVO.setMeasuringPoint("层前");

            //算法处理后的图片进行加密
            Map<String, Object> decodeImage = compressAndDecode(save_path);
            //封装加密图片json
            responseVO.setImageBase64(decodeImage);


            return new ResultInfo(true, responseVO);
        }
        return new ResultInfo(false, "获取算法处理异常");
    }

    //图片加密未压缩
    private Map<String, Object> getDecodeImage(String save_path) {
        byte[] bytes = new byte[0];
        try {
            //读取图片
            bytes = Files.toByteArray(new File(save_path));
        } catch (IOException e) {
            log.info("读取算法处理后的图片异常");
        }
        String encode = null;


        try {
            //Base64加密
            encode = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.info("解密失败");
        }
        //加密字符串拼接加密信息
        encode = "data:image/jpeg;base64," + encode;
        //将ResponseVo中的save_path 更新为加密字符串
        HashMap<String, Object> map = new HashMap<>();
        map.put("imageBase64", encode);
        return map;
    }

    //图片压缩加密
    public Map<String, Object> compressAndDecode(String save_path) {
        long start = System.currentTimeMillis();
        byte[] bs = null;
        try {
            bs = Files.toByteArray(new File(save_path));
        } catch (IOException e1) {
            log.info("{}:文件找不到", save_path);
        }
        ByteArrayInputStream intputStream = new ByteArrayInputStream(bs);
        Thumbnails.Builder<? extends InputStream> builder = Thumbnails.of(intputStream).scale(0.5).outputQuality(0.5);
        String encode = null;
        try {
            BufferedImage bufferedImage = builder.asBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            byte[] byteArray = baos.toByteArray();
            encode = Base64Utils.encodeToString(byteArray);
        } catch (IOException e) {
        }
        long end = System.currentTimeMillis();
        log.info("图片压缩加密耗时={}", (end - start));
        encode = "data:image/jpeg;base64," + encode;
        //将ResponseVo中的save_path 更新为加密字符串
        HashMap<String, Object> map = new HashMap<>();
        map.put("imageBase64", encode);
        return map;
    }
}