package com.asciiimage.asciiimage.process.matrix;

import java.util.ArrayList;

/**
 * TileGrayScaleMatrix 可以将一个大的GrayScaleMatrix分解为多个小的GrayScaleMatrix,每一块表示一个 tile，一个tile在图片中占据一个字符位置
 * TileGrayScaleMatrix 中包含一个数组存储所有的tile，一块tile的大小
 */
public class TileGrayScaleMatrix <T extends MyMatrix>{

    private int tileWidth,tileHeight;
    private int rows,columns;
    private ArrayList<T> tiles;

    public TileGrayScaleMatrix(T source,final int tileWidth,final int tileHeight){
        if(tileHeight <= 0 || tileWidth <= 0){
            throw new IllegalArgumentException("tile尺寸有误");
        }

        int width = source.getWidth(), height = source.getHeight();
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("grayScaleMatrix尺寸有误");
        }

        this.rows = height/tileHeight;
        this.columns = width/tileWidth;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        tiles = new ArrayList<T>(rows * columns);

        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                tiles.add((T)source.getSubMatrix(j * tileWidth,i * tileHeight,tileWidth,tileHeight));
            }
        }
    }

    public T getTile(int index){
        if(index < 0 || index >= rows * columns){
            throw new IllegalArgumentException("下标溢出");
        }
        return tiles.get(index);
    }

    public int getCounts(){
        return getColumns() * getRows();
    }

    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }

}
