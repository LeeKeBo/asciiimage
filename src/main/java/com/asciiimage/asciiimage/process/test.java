package com.asciiimage.asciiimage.process;

import com.asciiimage.asciiimage.process.asciiConvert.*;
import com.asciiimage.asciiimage.process.charFitStrategy.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
            Font font = new Font("Courier", Font.BOLD, 3);
            AsciiCache asciiCache = new AsciiCache(font);
            charFitStrategy charFitStrategy = new ssimFitStrategy();
            ImageConvert convert = new AsciiToImageConvert(asciiCache, charFitStrategy);
//            BufferedImage source = ImageIO.read(new File(
//                    "examples/portrait.png"));
//            BufferedImage result = (BufferedImage) convert.convertImage(source);
//            ImageIO.write(result, "png",
//                    new File("examples/portrait_small_square_error.png"));
//            gifConvert gifConverterTest = new gifConvert(asciiCache,charFitStrategy);
//            gifConverterTest.gitConverter("examples/test.gif","examples/test-result.gif",10,0);
        BufferedImage source2 = ImageIO.read(new File("examples/1.jpg"));
        BufferedImage convertRes = (BufferedImage) convert.convertImage(source2);
        ImageIO.write(convertRes, "png", new File( "examples/2.jpg"));


    }
}
