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
            Font font = new Font("Courier", Font.BOLD, 6);
            AsciiCache asciiCache = new AsciiCache(font,new char[] {'\\','|', ' ', '/','-'});
            charFitStrategy charFitStrategy = new ssimFitStrategy();
            ImageConvert convert = new AsciiToImageConvert(asciiCache, charFitStrategy);
            BufferedImage source = ImageIO.read(new File(
                    "examples/portrait.png"));
            BufferedImage result = (BufferedImage) convert.convertImage(source);
            ImageIO.write(result, "png",
                    new File("examples/portrait_small_square_error.png"));


    }
}
