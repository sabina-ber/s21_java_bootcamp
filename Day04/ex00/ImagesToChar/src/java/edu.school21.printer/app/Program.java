package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import java.io.IOException;

public class Program {
    private static String imagePath;
    private static char whiteChar;
    private static char blackChar;

    public static void main(String[] args) {
        if (args.length == 3) {
            imagePath = args[2];
            whiteChar = args[0].charAt(0);
            blackChar = args[1].charAt(0);
        } else {
            System.out.println("Usage: java Main [char_for_white] [char_for_black] [path_to_bmp]");
            return;
        }
        try {
            ImageConverter converter = new ImageConverter();
            String result = converter.convertImageToChars(imagePath, whiteChar, blackChar);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("An error occurred while converting the image: " + e.getMessage());
        }
    }
}