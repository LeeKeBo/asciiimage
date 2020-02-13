package com.asciiimage.asciiimage.process.asciiConvert;

import com.asciiimage.asciiimage.process.charFitStrategy.charFitStrategy;
import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AsciiToImageConvert extends ImageConvert<BufferedImage>{

    public AsciiToImageConvert(){
        super();
    }

    public AsciiToImageConvert(AsciiCache asciiCache, charFitStrategy charFitStrategy){
        super(asciiCache,charFitStrategy);
    }

    @Override
    protected void initOutput(int width, int height) {
        output = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void addTileToOutput(GrayScaleMatrix bestFit, int[] pixels, int row, int column, Dimension tileSize,final int imageWidth) {
        if(row < 0 || column < 0 )
            throw new IllegalArgumentException("坐标位置无效");
        float []data = bestFit.getData();
        for(int i=0;i<tileSize.height;i++){
            for(int j=0;j<tileSize.width;j++){
                int component  = (int)data[i*tileSize.width + j];
                pixels[(i+row)*imageWidth + j + column] = new Color(component,component,component).getRGB();
            }
        }
    }

    @Override
    protected void finalizeOutput(int[] pixels, Dimension tileSize) {
        output.setRGB(0,0,tileSize.width,tileSize.height,pixels,0,tileSize.width);
    }
}
