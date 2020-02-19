package com.asciiimage.asciiimage.process;

import com.asciiimage.asciiimage.process.asciiConvert.*;
import com.asciiimage.asciiimage.process.charFitStrategy.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Convert {
    private static AsciiCache asciiCache;
    private static AsciiToImageConvert defaultImageConvert = new AsciiToImageConvert();
    private static gifConvert defaultGifConvert = new gifConvert();
    private static charFitStrategy defaultCharFit = new ssimFitStrategy();
    private static Font font;

    public static ImageConvert CreateConverter(final String suffix, final int size, final String charSet) {
        if (size == 6 && (charSet == null || charSet.isEmpty())) {
            return suffix.equals("gif") ? defaultGifConvert : defaultImageConvert;
        }
        if ((charSet == null || charSet.isEmpty())) {
            if (size == 6) {
                return suffix.equals("gif") ? defaultGifConvert : defaultImageConvert;
            } else {
                font = new Font("Courier", Font.BOLD, size);
                asciiCache = new AsciiCache(font);
                return suffix.equals("gif") ? new gifConvert(asciiCache, defaultCharFit) : new AsciiToImageConvert(asciiCache, defaultCharFit);
            }
        } else {
            font = new Font("Courier", Font.BOLD, size);
            asciiCache = new AsciiCache(font, charSet.toCharArray());
            return suffix.equals("gif") ? new gifConvert(asciiCache, defaultCharFit) : new AsciiToImageConvert(asciiCache, defaultCharFit);
        }
    }
//
//    public static void main(String[] args) throws IOException {
//        Font font = new Font("Courier", Font.BOLD, 8);
//        AsciiCache asciiCache = new AsciiCache(font);
//        charFitStrategy charFitStrategy = new ssimFitStrategy();
//        ImageConvert convert = new AsciiToImageConvert(asciiCache, charFitStrategy);
////            BufferedImage source = ImageIO.read(new File(
////                    "examples/portrait.png"));
////            BufferedImage result = (BufferedImage) convert.convertImage(source);
////            ImageIO.write(result, "png",
////                    new File("examples/portrait_small_square_error.png"));
////            gifConvert gifConverterTest = new gifConvert(asciiCache,charFitStrategy);
//            //defaultImageConvert.gitCon
//
//        int i = defaultGifConvert.gifColorConverter("examples/test.gif","examples/12.gif",10,0);
//        System.out.println(i);
//        BufferedImage source2 = ImageIO.read(new File("examples/1.jpg"));
//        BufferedImage convertRes = (BufferedImage) convert.convertColorImage(source2);
//        ImageIO.write(convertRes, "png", new File("examples/2.jpg"));
//
//
//    }
}
