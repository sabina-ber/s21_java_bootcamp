import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Program {
    private static final Queue<Integer> downloadQueue = new LinkedList<>();
    public static void main(String[] args) {
        try {
            int threadsNum = Checker.checkParam(args);
            Map<Integer, String> downloadMap = FileContent.readDownloadMapFromFile("files_urls.txt");
            DownloadManager downloadManager = new DownloadManager(downloadMap, threadsNum);
            downloadManager.startDownloading();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
