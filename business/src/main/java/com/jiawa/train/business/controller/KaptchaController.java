package com.jiawa.train.business.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(("/kaptcha"))
public class KaptchaController {

    @Qualifier("getDefaultKaptcha")
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Resource
    public StringRedisTemplate stringRedisTemplate;

    @GetMapping("/image-code/{imageCodeToken}")
    public void imageCode(@PathVariable String imageCodeToken, HttpServletResponse httpServletResponse) throws IOException {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try{
            String createText = defaultKaptcha.createText();
            stringRedisTemplate.opsForValue().set(imageCodeToken,createText,300, TimeUnit.SECONDS);
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IOException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-control","no-store");
        httpServletResponse.setHeader("Pragma","no-cache");
        httpServletResponse.setDateHeader("Expires",0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
