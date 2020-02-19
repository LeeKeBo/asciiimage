package com.asciiimage.asciiimage.process.matrix;

import java.awt.*;
import java.lang.reflect.Array;

public class NormalMatrix extends MyMatrix {
    // data数组记录像素点的值
    private int[] data;
    private int width, height;

    public NormalMatrix(final int width, final int height) {
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("参数数据有误");
        else {
            this.data = new int[width * height];
            this.width = width;
            this.height = height;
        }
    }

    public NormalMatrix(final int[] data, final int width, final int height) {
        this(width, height);

        if (data.length != width * height)
            throw new IllegalArgumentException("参数数据有误");

        for (int i = 0; i < width * height; i++) {
            this.data[i] = initData(data[i]);
        }
    }

    private int initData(final int rgbColor){
        int r = (rgbColor >> 16) & 0XFF;
        int g = (rgbColor >> 8) & 0XFF;
        int b = rgbColor & 0xFF;
        return (r<<16) + (g<<8) + b;
    }

    /**
     * 获得一个矩阵的一部分，即切割出一个子矩阵
     *
     * @param beginWidth
     * @param beginHeight
     * @param tileWidth
     * @param tileHeight
     * @return
     */
    public NormalMatrix getSubMatrix(final int beginWidth, final int beginHeight, final int tileWidth, final int tileHeight) {
        if (tileHeight <= 0 || tileWidth <= 0 || tileHeight > height || tileWidth > width) {
            throw new IllegalArgumentException("tile尺寸有误");
        }
        // 子矩阵的第一个位置的下标
        int beginIndex = beginHeight * width + beginWidth;

        NormalMatrix subMatrix = new NormalMatrix(tileWidth, tileHeight);

        for (int i = 0; i < tileHeight; i++) {
            for (int j = 0; j < tileWidth; j++) {
                subMatrix.setData(data[i * width + j + beginIndex], i * tileWidth + j);
            }
        }
        return subMatrix;
    }

    public int[] getAvgColor() {
        int array[] = new int[3];
        if (this.data == null)
            throw new NullPointerException("data未初始化");
        int r = 0, g = 0, b = 0;
        for (int i = 0; i < width * height; i++) {
            r += (data[i] >> 16) & 0XFF;
            g += (data[i] >> 8) & 0XFF;
            b += data[i] & 0xFF;
        }
        r /= width * height;
        g /= width * height;
        b /= width * height;
        array[0] = r;
        array[1] = g;
        array[2] = b;
        return array;

    }

    public int[] getData() {
        return data;
    }

    public float getData(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("index 越界");
        }
        return data[index];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setData(int data, final int index) {
        //System.out.println(this.data.length);
        if (index < 0 || index >= this.data.length) {
            System.out.println(this.data.length);
            throw new IllegalArgumentException("index 越界");
        }
        this.data[index] = data;
    }


}
