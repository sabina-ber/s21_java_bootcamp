package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    public String convertImageToChars(String imagePath, char whiteChar, char blackChar) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);
                if (color == Color.WHITE.getRGB()) {
                    result.append(whiteChar);
                } else {
                    result.append(blackChar);
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}
