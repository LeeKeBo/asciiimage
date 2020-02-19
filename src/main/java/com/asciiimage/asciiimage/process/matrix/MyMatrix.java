package com.asciiimage.asciiimage.process.matrix;

public abstract class MyMatrix {
    public abstract MyMatrix getSubMatrix(final int beginWidth, final int beginHeight, final int tileWidth, final int tileHeight);
    public abstract int getWidth();
    public abstract int getHeight();
}
