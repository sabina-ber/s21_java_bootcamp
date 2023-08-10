package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import edu.school21.printer.logic.Args;
import java.io.IOException;
import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) throws IOException {
        Args commandLineArgs = new Args();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(commandLineArgs)
                .build();
        jCommander.parse(args);
        ImageConverter imageConverter = new ImageConverter();
        imageConverter.changeColor(commandLineArgs.getWhite(), commandLineArgs.getBlack(), "src/resources/it.bmp");
    }
}