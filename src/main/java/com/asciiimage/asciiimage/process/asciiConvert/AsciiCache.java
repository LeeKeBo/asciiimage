package com.asciiimage.asciiimage.process.asciiConvert;

import com.asciiimage.asciiimage.process.matrix.GrayScaleMatrix;
import com.asciiimage.asciiimage.process.matrix.NormalMatrix;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AsciiCache {

    private Dimension tileSize  = new Dimension();                            // 字符集图片矩阵尺寸
    private Map<Character,GrayScaleMatrix> imageCache;      // 记录所有的字符集图片矩阵
    private Font font;
    private static char[] defaultChar = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "
            .toCharArray();                                 // 默认使用的字符集
    private static Font defaultFont = new Font("Courier", Font.BOLD, 6);

    public AsciiCache(){
        this(defaultFont,defaultChar);
    }

    public AsciiCache(final Font font){
        this(font,defaultChar);
    }

    /**
     * 根据输入的字体和字符集创建每个字符集的图片缓存
     * @param font          字体
     * @param characters    字符集
     */
    public AsciiCache(final Font font,final char[] characters){
        this.font = font;
        calculateSize(font,characters);                     // 计算尺寸
        createCharactersCache(font,characters,tileSize);    // 创建字符图片缓存
    }

    public Dimension getTileSize() {
        return tileSize;
    }

    public Map<Character, GrayScaleMatrix> getImageCache() {
        return imageCache;
    }


    /**
     * 得到使用指定字体和字符集创建的图片缓存的图片，此为之后拼装图片的最小尺寸
     * @param font          字体
     * @param characters    字符集
     * @return
     */
    private void calculateSize(final Font font,final char[] characters)
    {

        BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(font);
        FontMetrics fm = graphics2D.getFontMetrics();

        for(int i=0;i<characters.length;i++){
            String character = Character.toString(characters[i]);
            Rectangle rectangle = new TextLayout(character,fm.getFont(),
                    fm.getFontRenderContext()).getOutline(null).getBounds();
            if(tileSize.width < rectangle.getWidth())
                tileSize.width = (int)rectangle.getWidth();
            if(tileSize.height < rectangle.getHeight())
                tileSize.height = (int)rectangle.getHeight();
        }
    }

    /**
     * 根据输入的字体，字符集和字体图片大小，生成字体缓存
     * @param font          字体
     * @param characters    字符集
     * @param tileSize      字体图片大小
     */
    private void createCharactersCache(final Font font,final char[] characters,final Dimension tileSize){
        this.imageCache = new HashMap<>();

        BufferedImage img = new BufferedImage(tileSize.width,tileSize.height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D)img.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(font);
        FontMetrics fm = graphics2D.getFontMetrics();

        for(int i=0;i<characters.length;i++){
            String character = Character.toString(characters[i]);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0,0,tileSize.width,tileSize.height);        //白底
            graphics2D.setColor(Color.BLACK);
            Rectangle rect = new TextLayout(character,fm.getFont(),fm.getFontRenderContext())
                    .getOutline(null).getBounds();

            // 此处存疑
            graphics2D.drawString(character,0,(int)(rect.getHeight() - rect.getMaxY()));    // 此处的y是字符串基线位置的坐标
//            System.out.println("Y: " + rect.getY());
//            System.out.println("Height: " + rect.getHeight());

            int []pixels = img.getRGB(0,0,tileSize.width,tileSize.height,null,0,tileSize.width);
            GrayScaleMatrix matrix = new GrayScaleMatrix(pixels,tileSize.width,tileSize.height);
            this.imageCache.put(characters[i],matrix);
        }
    }

    public NormalMatrix getColorMatrix(int[] color,char charToDraw){
        BufferedImage img = new BufferedImage(tileSize.width,tileSize.height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D)img.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(font);
        FontMetrics fm = graphics2D.getFontMetrics();

        String character = Character.toString(charToDraw);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,tileSize.width,tileSize.height);        //白底
        graphics2D.setColor(new Color(color[0],color[1],color[2]));
        Rectangle rect = new TextLayout(character,fm.getFont(),fm.getFontRenderContext())
                .getOutline(null).getBounds();

        // 此处存疑
        graphics2D.drawString(character,0,(int)(rect.getHeight() - rect.getMaxY()));    // 此处的y是字符串基线位置的坐标
//            System.out.println("Y: " + rect.getY());
//            System.out.println("Height: " + rect.getHeight());

        int []pixels = img.getRGB(0,0,tileSize.width,tileSize.height,null,0,tileSize.width);
        NormalMatrix matrix = new NormalMatrix(pixels,tileSize.width,tileSize.height);
        return matrix;
    }


}
