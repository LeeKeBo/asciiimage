package com.asciiimage.asciiimage.process.charFitStrategy;

import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;
import com.asciiimage.asciiimage.process.matrix.NormalMatrix;

/**
 */
public class ssimFitStrategy implements charFitStrategy {

    /**
     * 使用结构相似度算法判断两个图片之间的差异程度，具体参考 https://zh.wikipedia.org/wiki/%E7%B5%90%E6%A7%8B%E7%9B%B8%E4%BC%BC%E6%80%A7
     * 因为两个图片都为灰度图（character的像素点为纯黑纯白也为灰度图），因此，均值为像素点的值，方差为 0
     * 这里还需计算两个图片各像素点的协方差，这里可认为为两个图片的各像素点之间无相关性，即图片1不会影响图片2的像素点分布，因此协方差为 1
     * @param character     字母图片
     * @param tile          对比图片
     * @return              相似度
     */
    @Override
    public float calculateError(GrayScaleMatrix character, GrayScaleMatrix tile) {
        float score = 0;
        float []characterData = character.getData(),tileData = tile.getData();
        float C1 = 0.00001f,C2 = 0.00003f;          // C1, C2 为两个较小的数，用来防止出现为0的情况
        for(int i=0;i<characterData.length;i++){
            score += (2 * characterData[i] * tileData[i] +C1) * (2 + C2)            // 此处的均值为像素点的RGB值任一（灰度图RGB各分量相等），协方差为1
                    /(characterData[i] * characterData[i] + tileData[i] * tileData[i] + C1 ) / C2;  //  此处方差为0
        }
        return 1 - score/characterData.length;
    }

    @Override
    public float calculateError(GrayScaleMatrix character, NormalMatrix tile) {
        float score = 0;
        float []characterData = character.getData();
        int []tileData = tile.getData();
        float C1 = 0.00001f,C2 = 0.00003f;          // C1, C2 为两个较小的数，用来防止出现为0的情况
        for(int i=0;i<characterData.length;i++){
            score += (2 * characterData[i] * tileData[i] +C1) * (2 + C2)            // 此处的均值为像素点的RGB值任一（灰度图RGB各分量相等），协方差为1
                    /(characterData[i] * characterData[i] + tileData[i] * tileData[i] + C1 ) / C2;  //  此处方差为0
        }
        return 1 - score/characterData.length;
    }
}
