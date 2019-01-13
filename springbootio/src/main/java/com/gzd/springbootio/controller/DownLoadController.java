package com.gzd.springbootio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author gzd
 * @date create in 2019/1/13 14:11
 * springBoot 实现下载文件
 **/
@RestController
public class DownLoadController {

    private final Logger  logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/download")
    public Object download(HttpServletResponse response){

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        String path = null;
        //设置强制下载不打开
        response.setContentType("application/force-download");
        //设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + "airQuality.zip");
        try{
            //获取项目目录，目录后面暂时写死
            path = ResourceUtils.getURL("classpath:").getPath()+File.separator+"download"+File.separator+"zip";
            String resultFile = "D:\\123.txt";
            byte[] buffer = new byte[1024];
            File file = new File(resultFile);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int b ;
            while ((b= bis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
            return "下载成功";
        }catch(Exception e){
            logger.error(e.getMessage());
            return  "下载失败";
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
