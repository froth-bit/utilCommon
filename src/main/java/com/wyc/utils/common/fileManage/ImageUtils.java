package com.wyc.utils.common.fileManage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    /**
     * 图片转Base64
     * @param LoadPath
     * @param DataName
     * @return
     */
    public static String TransformPhotoToBase64Data(String LoadPath,String DataName){
        Base64.Encoder encoder= Base64.getEncoder();  //获取Base64编码器
        byte [] ImgContainer = null ;    //数据集缓存器
        FileInputStream fileInputStream = null; //文件输入流
        try {
            System.out.println(LoadPath+DataName);
            fileInputStream = new FileInputStream(LoadPath+DataName);    //到指定路径寻找文件
            ImgContainer = new byte[fileInputStream.available()];          //设置图片字节数据缓冲区大小
            fileInputStream.read(ImgContainer);           //将数据流中的图片数据读进缓冲区
            String Base64ImgData =encoder.encodeToString(ImgContainer);  //将图片编码转换成Base64格式的数据集
            fileInputStream.close();      //关闭数据流
            return Base64ImgData;  //将缓冲区数据转换成字符数据返回
        } catch (FileNotFoundException e) {
            return "找不到指定文件!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }

}
