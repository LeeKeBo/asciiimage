package com.asciiimage.asciiimage.process.charFitStrategy;

import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;
import com.asciiimage.asciiimage.process.matrix.MyMatrix;
import com.asciiimage.asciiimage.process.matrix.NormalMatrix;

/**
 * 通过计算每个两个矩阵每个位置的像素点差的平方和，最后做平均，得到两个图像的差异性
 */
public class avgSquareFitStrategy implements charFitStrategy {

    @Override
    public float calculateError(GrayScaleMatrix character, GrayScaleMatrix tile) {
        float error = 0;
        float []characterData = character.getData(),tileData = tile.getData();
        for(int i=0;i<characterData.length;i++){
            error += (characterData[i] - tileData[i]) * (characterData[i] - tileData[i]);
        }
        return error/characterData.length;
    }

    @Override
    public float calculateError(GrayScaleMatrix character, NormalMatrix tile) {
        float error = 0;
        float []characterData = character.getData();
        int [] tileData = tile.getData();
        for(int i=0;i<characterData.length;i++){
            error += (characterData[i] - tileData[i]) * (characterData[i] - tileData[i]);
        }
        return error/characterData.length;
    }


}
