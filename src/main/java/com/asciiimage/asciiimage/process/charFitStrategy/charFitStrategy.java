package com.asciiimage.asciiimage.process.charFitStrategy;

import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;
import com.asciiimage.asciiimage.process.matrix.MyMatrix;
import com.asciiimage.asciiimage.process.matrix.NormalMatrix;

public interface charFitStrategy {
    public float calculateError(GrayScaleMatrix character, GrayScaleMatrix tile);
    public float calculateError(GrayScaleMatrix character, NormalMatrix tile);

}
