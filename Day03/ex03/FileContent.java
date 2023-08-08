import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileContent {

    public static HashMap<Integer, String> readDownloadMapFromFile(String link) {
        HashMap<Integer, String> downloadMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(link))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    int number = Integer.parseInt(parts[0]);
                    String url = parts[1];
                    downloadMap.put(number, url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadMap;
    }
}