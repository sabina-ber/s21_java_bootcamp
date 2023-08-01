import java.io.*;
import java.util.Map;

public class SignaturesReader {

    public static Map<String, String> readSignaturesFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Map <String, String> signaturesMap = new java.util.HashMap<>();
            File file = new File(fileName);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] signature = line.split(",");
                if (signature.length == 2) {
                    String fileType = signature[0].trim();
                    String hexSignature = signature[1].replaceAll("\\s+", "");
                    signaturesMap.put(hexSignature, fileType);
                } else {
                    System.out.println("Invalid line in " + fileName + ": " + line);
                }
            }
            return signaturesMap;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return null;
        }
    }
}
