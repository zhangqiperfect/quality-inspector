package com.quality.inspector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import com.quality.inspector.pojo.SolarReturn;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.security.jgss.HttpCaller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class QualityInspectorApplicationTests {
//        @Test
//        public void test() {
//            byte[] bs = null;
//            try {
//                bs = Files.toByteArray(new File("E:/桌面/image/1P6723024170182.jpg"));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            ByteArrayInputStream intputStream = new ByteArrayInputStream(bs);
//            Thumbnails.Builder<? extends InputStream> builder = Thumbnails.of(intputStream).scale(0.5).outputQuality(0.5);
//            String encode = null;
//            try {
//                BufferedImage bufferedImage = builder.asBufferedImage();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(bufferedImage, "jpg", baos);
//                byte[] byteArray = baos.toByteArray();
//                encode = Base64Utils.encodeToString(byteArray);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        @Test
        public void test2() throws IOException {
            Thumbnails.of(new File("E:/桌面/image/1.jpg")).scale(0.3).outputQuality(0.3).toFile("E:/桌面/image/3.jpg");
//            Thumbnails.of(new File("E:/桌面/image/1.jpg")).size(20, 30).toFile("E:/桌面/image/3.jpg");
        }
}
