package com.asciiimage.asciiimage.process.matrix;


/**
 * GrayScaleMatrix类存有一个矩阵，矩阵记录RGB图片转化为灰度值后的像素点数组
 */
public class GrayScaleMatrix {
    // data数组记录像素点的值
    private float[] data;
    private int width, height;


    /**
     * 构造函数
     *
     * @param width
     * @param height
     */
    public GrayScaleMatrix(final int width, final int height) {
        data = new float[width * height];
        this.width = width;
        this.height = height;
    }

    /**
     * 构造函数
     *
     * @param source
     * @param width
     * @param height
     */
    public GrayScaleMatrix(final int[] source, final int width, final int height) {
        this(width, height);
//        System.out.println(width);
//        System.out.println(height);

        if (width * height != source.length) {
            throw new IllegalArgumentException("像素点数组大小不匹配");
        }

        for (int i = 0; i < width * height; i++) {
            // 将得到的RGB数组转为灰度值的数组
            this.data[i] = changeRGBToGray(source[i]);
        }

    }

    /**
     * 获得一个矩阵的一部分，即切割出一个子矩阵
     * @param beginWidth
     * @param beginHeight
     * @param tileWidth
     * @param tileHeight
     * @return
     */
    public GrayScaleMatrix getSubMatrix(final int beginWidth, final int beginHeight, final int tileWidth, final int tileHeight) {
        if (tileHeight <= 0 || tileWidth <= 0 || tileHeight > height || tileWidth > width) {
            throw new IllegalArgumentException("tile尺寸有误");
        }
        // 子矩阵的第一个位置的下标
        int beginIndex = beginHeight * width + beginWidth;

        GrayScaleMatrix subMatrix = new GrayScaleMatrix(tileWidth, tileHeight);

        for (int i = 0; i < tileHeight; i++) {
            for (int j = 0; j < tileWidth; j++) {
                subMatrix.setData(data[i * width + j + beginIndex], i * tileWidth + j);
            }
        }

        return subMatrix;

    }

    /**
     * 将RGB值使用加权法转为灰度值
     *
     * @param rgbColor RGB颜色值
     * @return 灰度值
     */
    private float changeRGBToGray(int rgbColor) {
        // 从颜色中提取出r,g,b三原色
        int r = (rgbColor >> 16) & 0XFF;
        int g = (rgbColor >> 8) & 0XFF;
        int b = rgbColor & 0xFF;

        // 使用加权法获得灰度值，人眼对不同颜色的敏感度不同，实际得到为亮度值，
        return (0.3f * r + 0.59f * g + 0.11f * b);
    }

    public float[] getData() {
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

    public void setData(float data, final int index) {
        //System.out.println(this.data.length);
        if (index < 0 || index >= this.data.length) {
            System.out.println(this.data.length);
            throw new IllegalArgumentException("index 越界");
        }
        this.data[index] = data;
    }


}
