package com.asciiimage.asciiimage.process.asciiConvert;

import com.asciiimage.asciiimage.process.charFitStrategy.charFitStrategy;
import com.asciiimage.asciiimage.process.charFitStrategy.ssimFitStrategy;
import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;
import com.asciiimage.asciiimage.process.matrix.NormalMatrix;
import com.asciiimage.asciiimage.process.matrix.TileGrayScaleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @param <Output> 图片转换器的输出类型，有图片和字符串两种
 */
public abstract class ImageConvert<Output> {
    protected AsciiCache charCache;         // 要转化为的字符集
    protected charFitStrategy strategy;     // 字符替代算法
    protected Output output;
    //protected

    public ImageConvert(){
        this.charCache = new AsciiCache();
        this.strategy = new ssimFitStrategy();
    }

    public ImageConvert(AsciiCache charCache,charFitStrategy strategy){
        this.charCache = charCache;
        this.strategy = strategy;
    }

    public Output convertImage(final BufferedImage source){
        Dimension tileSize = charCache.getTileSize();
        Dimension outputSize = new Dimension();
        // 输出图片的尺寸需做切除边界处理，防止出现不到一个字符大小的情况，因此将输出图片的
        // 宽和高都设置为 tileSize 的倍数
        outputSize.width = (source.getWidth() / tileSize.width) * tileSize.width;
        outputSize.height = (source.getHeight() / tileSize.height) * tileSize.height;

        // 最后一个参数表示两行之间的索引差，
        int []pixels = source.getRGB(0,0,outputSize.width,outputSize.height,null,0,outputSize.width);

        GrayScaleMatrix grayScaleMatrix = new GrayScaleMatrix(pixels,outputSize.width,outputSize.height);
        TileGrayScaleMatrix<GrayScaleMatrix> tileGrayScaleMatrix = new TileGrayScaleMatrix<GrayScaleMatrix>(grayScaleMatrix,tileSize.width,tileSize.height);
        int count = tileGrayScaleMatrix.getCounts();

        Map<Character, GrayScaleMatrix> charactersMap = charCache.getImageCache();

        //BufferedImage output = new BufferedImage(outputSize.width,outputSize.height,BufferedImage.TYPE_INT_RGB);
        initOutput(outputSize.width,outputSize.height);

        for(int i=0;i<count;i++){
            GrayScaleMatrix tile = tileGrayScaleMatrix.getTile(i);
            GrayScaleMatrix bestFit = null;   // 得到最合适的替换矩阵
            float minError = Float.MAX_VALUE;
            Map<Character,GrayScaleMatrix> imageCache = charCache.getImageCache();

            // 遍历，计算tile与charCache的相似度，选出相似度最高的作为替换的
            for(Map.Entry<Character,GrayScaleMatrix> temp : imageCache.entrySet()){
                float error = strategy.calculateError(temp.getValue(),tile);
                if(error < minError){
                    minError = error;
                    bestFit = temp.getValue();
                }
            }
            int columns = tileGrayScaleMatrix.getColumns();
            int row = i/columns,column = i%columns;
            addTileToOutput(bestFit,pixels,row*tileSize.height,column*tileSize.width,tileSize,outputSize.width);

        }
        finalizeOutput(pixels,outputSize);
        return output;
    }

    public Output convertColorImage(final BufferedImage source){
        Dimension tileSize = charCache.getTileSize();
        Dimension outputSize = new Dimension();
        // 输出图片的尺寸需做切除边界处理，防止出现不到一个字符大小的情况，因此将输出图片的
        // 宽和高都设置为 tileSize 的倍数
        outputSize.width = (source.getWidth() / tileSize.width) * tileSize.width;
        outputSize.height = (source.getHeight() / tileSize.height) * tileSize.height;

        // 最后一个参数表示两行之间的索引差，
        int []pixels = source.getRGB(0,0,outputSize.width,outputSize.height,null,0,outputSize.width);
        NormalMatrix normalMatrix = new NormalMatrix(pixels,outputSize.width,outputSize.height);
//        GrayScaleMatrix grayScaleMatrix = new GrayScaleMatrix(pixels,outputSize.width,outputSize.height);
        TileGrayScaleMatrix<NormalMatrix> tileGrayScaleMatrix = new TileGrayScaleMatrix<NormalMatrix>(normalMatrix,tileSize.width,tileSize.height);
        int count = tileGrayScaleMatrix.getCounts();

        Map<Character, GrayScaleMatrix> charactersMap = charCache.getImageCache();

        //BufferedImage output = new BufferedImage(outputSize.width,outputSize.height,BufferedImage.TYPE_INT_RGB);
        initOutput(outputSize.width,outputSize.height);

        for(int i=0;i<count;i++){
            NormalMatrix tile = tileGrayScaleMatrix.getTile(i);
            char bestFit = 'a';   // 得到最合适的替换矩阵
            float minError = Float.MAX_VALUE;
            Map<Character,GrayScaleMatrix> imageCache = charCache.getImageCache();

            // 遍历，计算tile与charCache的相似度，选出相似度最高的作为替换的
            for(Map.Entry<Character,GrayScaleMatrix> temp : imageCache.entrySet()){
                float error = strategy.calculateError(temp.getValue(),tile);
                if(error < minError){
                    minError = error;
                    bestFit = temp.getKey();
                }
            }
            int columns = tileGrayScaleMatrix.getColumns();
            int row = i/columns,column = i%columns;
            int[] color = tile.getAvgColor();
            NormalMatrix matrix = charCache.getColorMatrix(color,bestFit);
            addTileToOutput(matrix,pixels,row*tileSize.height,column*tileSize.width,tileSize,outputSize.width);

        }
        finalizeOutput(pixels,outputSize);
        return output;
    }


    /**
     * 初始化 output 的尺寸
     * @param width     宽
     * @param height    高
     */
    protected abstract void initOutput(final int width,final int height);


    /**
     * 将找到的字母矩阵根据位置和尺寸替换进原图像素点
     * @param bestFit   找到的最合适的替换字母矩阵
     * @param pixels    要被替换的原图像素点矩阵
     * @param row       起始行
     * @param column    起始列
     * @param tileSize  字母矩阵尺寸
     */
    protected abstract void addTileToOutput(GrayScaleMatrix bestFit,int []pixels,final int row,final int column,Dimension tileSize,final int imageWidth);

    protected abstract void addTileToOutput(NormalMatrix bestFit,int []pixels,final int row,final int column,Dimension tileSize,final int imageWidth);


    /**
     * 将最后的像素点写进 output
     * @param pixels    像素点矩阵
     * @param tileSize  字母矩阵尺寸
     */
    protected abstract void finalizeOutput(int []pixels,Dimension tileSize);

}
