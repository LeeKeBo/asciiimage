package com.asciiimage.asciiimage.utils;

import java.util.Random;

public class GenerateRandomName {
    private static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static int strLength = 62;

    /**
     * 生成随机文件名的静态方法
     * @param length 生成的随机文件名的长度
     * @param suffix 文件后缀名
     * @return  随机文件名
     */
    public static String GenerateName(final int length,final String suffix){
        Random random = new Random();
        StringBuffer name = new StringBuffer();
        for(int i=0;i<length;i++){
            int pos = random.nextInt(strLength);
            name.append(str.charAt(pos));
        }
        return name.toString()+suffix;
    }

    /**
     * @param length 生成的随机文件名的长度
     * @return  随机文件名
     */
    public static String GenerateName(final int length){
        return GenerateName(length,"");
    }
}
