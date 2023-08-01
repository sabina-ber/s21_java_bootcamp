import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) throws IOException {
        Map<String, String> signaturesMap = SignaturesReader.readSignaturesFile("signatures.txt");
        Scanner scanner = new Scanner(System.in);
        String filePath;
        while (true) {
            filePath = scanner.nextLine();
            if (filePath.equals("42")) {
                break;
            }
            FileParser.parseSignaturesFromFile(filePath, signaturesMap);
        }
        scanner.close();
    }
}


