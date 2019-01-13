package com.gzd.springbootio.util;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

/**
 * @author gzd
 * @date create in 2019/1/7 18:27
 **/
public class ZipUtils {
    /**
     * 通过打包APi直接操作，未封装
     * */
    public static void downloadZip(){

        ZipUtil.pack(new File("D:\\123"), new File("d:\\demo.zip"));

    }

    public static void main(String[] args){

        downloadZip();
    }
}
