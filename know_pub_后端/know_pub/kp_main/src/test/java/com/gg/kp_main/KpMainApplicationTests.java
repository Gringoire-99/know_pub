package com.gg.kp_main;

import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.sys.MenuMapper;
import com.gg.kp_common.feign.OssFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
class KpMainApplicationTests {
   @Autowired
    OssFeignClient ossFeignClient;

    @Test
    void test1() throws IOException {
        String imageUrl = "http://localhost:8081/47236d7d-7ac2-4dac-9aba-e0750662e106";
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        BufferedImage image = ImageIO.read(inputStream);

    }
    public String getFileFormat(String imageUrl) {
        String[] formatNames = ImageIO.getReaderFormatNames();
        for (String format : formatNames) {
            if (imageUrl.toLowerCase().endsWith(format)) {
                return format;
            }
        }
        return null;
    }
    public BufferedImage isValidImage(String imageUrl) {
        BufferedImage image;
        URL url = null;
        try {
            url = new URL(imageUrl);
            image = ImageIO.read(url);

        } catch (IOException e) {
            throw new SystemException("url无效");
        }

        // 检查图片是否有效
        if (image == null) {
            throw new SystemException("图片无效");
        }

        // 检查图片格式
        String format = getFileFormat(imageUrl);
        if (format == null || !(format.equals("jpg") || format.equals("png"))) {
            throw new SystemException("格式无效");
        }

        // 检查图片大小
        int width = image.getWidth();
        int height = image.getHeight();
        int maxDimension = 1000; // 设置最大尺寸阈值
        if (width > maxDimension || height > maxDimension) {
            throw new SystemException("图片过大");
        }

        return image;
    }
}
