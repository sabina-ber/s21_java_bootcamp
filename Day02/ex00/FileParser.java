import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class FileParser {
    public static void parseSignaturesFromFile(String filePath, Map<String, String> signatureMap) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }
        byte[] buffer = new byte[8];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(buffer, 0, 8);
            String hex = bytesToHex(buffer);
            if (checkSignature(hex, signatureMap)) {
                writeSignature(hex, signatureMap);
                System.out.println("Processed");
            } else {
                System.out.println("Undefined");
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private static boolean checkSignature(String hex, Map<String, String> signatureMap) {
        return signatureMap.containsKey(hex);
    }

    private static void writeSignature(String hex, Map<String, String> signatureMap) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("result.txt", true)) {
            fos.write((signatureMap.get(hex) + "\n").getBytes());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            throw e;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }
}
