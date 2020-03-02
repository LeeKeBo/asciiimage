package com.asciiimage.asciiimage.controller;

import com.asciiimage.asciiimage.process.Convert;
import com.asciiimage.asciiimage.process.asciiConvert.AsciiCache;
import com.asciiimage.asciiimage.process.asciiConvert.AsciiToImageConvert;
import com.asciiimage.asciiimage.process.asciiConvert.ImageConvert;
import com.asciiimage.asciiimage.process.asciiConvert.gifConvert;
import com.asciiimage.asciiimage.utils.GenerateRandomName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
//@RequestMapping("/Image")
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}
)
public class ImageController {
    @Value("${uploadDir}")
    private String uploadDir;
    @Value("${targetDir}")
    private String targetDir;
    @Value("${pictureUrl}")
    private String url;
    private int fileNameLength = 10;
    private Result result = new Result();
    private ImageConvert imageConvert;
    private gifConvert gifConverter;
    private Convert convert = new Convert();

    @PostMapping("/Image")
    @ResponseBody
    public Result postImageToConvert(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            result.setStatus(false);
            result.setMessage("未上传文件");
        } else {
            // 得到字符集和字体大小
            String charSet = request.getParameter("charSet");
            int size = Integer.parseInt(request.getParameter("size"));
            String type = request.getParameter("type");
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String suffixWithoutPoint = suffix.substring(1);
            String name = GenerateRandomName.GenerateName(fileNameLength, suffix);
            File source = new File(new File(uploadDir).getAbsolutePath() + "/" + name);
            // 若父文件夹不存在，创建该文件夹
            if (!source.getParentFile().exists())
                source.getParentFile().mkdirs();
            File target = new File(new File(targetDir).getAbsolutePath()+"/" + name);
            if(!target.getParentFile().exists())
                target.getParentFile().mkdirs();
            try {
                // 保存文件
                file.transferTo(source);
                // 获取变换器完成图片转变
                if (suffixWithoutPoint.equals("gif")) {
                    gifConverter = (gifConvert) convert.CreateConverter(suffixWithoutPoint, size, charSet);
                    if(type.equals("color"))
                        gifConverter.gifColorConverter(uploadDir + name, targetDir + name, 10, 0);
                    else
                        gifConverter.gifConverter(uploadDir + name, targetDir + name, 10, 0);
                } else {
                    imageConvert = (AsciiToImageConvert) convert.CreateConverter(suffixWithoutPoint, size, charSet);
                    BufferedImage convertRes = null;
                    System.out.println(type);
                    if(type.equals("color"))
                        convertRes = (BufferedImage) imageConvert.convertColorImage(ImageIO.read(source));
                    else
                        convertRes = (BufferedImage) imageConvert.convertImage(ImageIO.read(source));
                    ImageIO.write(convertRes, "png",target);
                }

                result.setStatus(true);
                result.setFileName(url + name);
            } catch (FileNotFoundException e){
                e.printStackTrace();
                result.setMessage(e.getMessage());
                result.setStatus(false);
            } catch (Exception e) {
                e.printStackTrace();
                result.setStatus(false);
                result.setMessage(e.getMessage());
            }
        }
        return result;
    }

    @GetMapping("/Image")
    public Result getImage(@RequestParam(value = "fileName") final String fileName) {
        if (fileName.isEmpty()) {
            result.setStatus(false);
            result.setMessage("文件名为空");
        } else {

        }
        return result;
    }

    class Result {
        private boolean status;
        private String fileName;
        private String message;

        public boolean isStatus() {
            return status;
        }

        public String getFileName() {
            return fileName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }


        /**
         * @param status true代表正常,false代表有误
         */
        public void setStatus(boolean status) {
            this.status = status;
        }
    }


}
