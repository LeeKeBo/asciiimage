package com.asciiimage.asciiimage.process.charFitStrategy;

import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;

public interface charFitStrategy {
    public float calculateError(GrayScaleMatrix character,GrayScaleMatrix tile);

}
