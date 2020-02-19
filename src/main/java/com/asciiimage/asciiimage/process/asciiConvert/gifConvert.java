package com.asciiimage.asciiimage.process.asciiConvert;

import com.asciiimage.asciiimage.process.charFitStrategy.charFitStrategy;

import com.asciiimage.asciiimage.process.utils.*;

/**
 * gif的转换可以通过继承之前的图片转换器来实现，只需将GIF逐帧读取后，对每一帧都进行一次变化后再存入即可
 */
public class gifConvert extends AsciiToImageConvert{

    public gifConvert(){
        super();
    }

    public gifConvert(AsciiCache asciiCache, charFitStrategy charFitStrategy) {
        super(asciiCache, charFitStrategy);
    }

    public int gifConverter(final String srcPath,final String targetPath){
        return gifConverter(srcPath,targetPath,10,0);
    }

    public int gifConverter(String srcPath,String targetPath,final int delay,final int repeat){
        GifDecoder gifDecoder = new GifDecoder();

        int status = gifDecoder.read(srcPath);
        if(status == 0){
            AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
            boolean targetStatus = animatedGifEncoder.start(targetPath);
            if(targetStatus){
                animatedGifEncoder.setDelay(delay);
                animatedGifEncoder.setRepeat(repeat);
                int count = gifDecoder.getFrameCount();
                for(int i=0;i<count;i++){
                    animatedGifEncoder.addFrame(convertImage(gifDecoder.getFrame(i)));
                }
                animatedGifEncoder.finish();
                return 1;       // 成功转换
            }
            else{
                return 0;       // 目标文件路径有误
            }
        }
        else{
            return -1;          // 源文件路径有误
        }
    }

    public int gifColorConverter(String srcPath,String targetPath,final int delay,final int repeat){
        GifDecoder gifDecoder = new GifDecoder();

        int status = gifDecoder.read(srcPath);
        if(status == 0){
            AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
            boolean targetStatus = animatedGifEncoder.start(targetPath);
            if(targetStatus){
                animatedGifEncoder.setDelay(delay);
                animatedGifEncoder.setRepeat(repeat);
                int count = gifDecoder.getFrameCount();
                for(int i=0;i<count;i++){
                    animatedGifEncoder.addFrame(convertColorImage(gifDecoder.getFrame(i)));
                }
                animatedGifEncoder.finish();
                return 1;       // 成功转换
            }
            else{
                return 0;       // 目标文件路径有误
            }
        }
        else{
            return -1;          // 源文件路径有误
        }
    }

}
