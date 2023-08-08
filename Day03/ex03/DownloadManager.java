import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;

public class DownloadManager {

    private final Queue<Integer> downloadQueue = new LinkedList<>();
    private final Map<Integer, String> downloadMap;
    private final int threadsNum;

    public DownloadManager(Map<Integer, String> downloadMap, int threadsNum) {
        this.downloadMap = downloadMap;
        this.threadsNum = threadsNum;
        this.downloadQueue.addAll(downloadMap.keySet());
    }

    public void startDownloading() throws InterruptedException {
        Thread[] downloadThreads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            downloadThreads[i] = new Thread(() -> {
                while (true) {
                    Integer fileId;
                    synchronized (downloadQueue) {
                        if (downloadQueue.isEmpty()) {
                            return;
                        }
                        fileId = downloadQueue.poll();
                    }
                    if (fileId != null) {
                        new Downloader(fileId, downloadMap.get(fileId)).run();
                    }
                }
            });
            downloadThreads[i].start();
        }

        for (Thread thread : downloadThreads) {
            thread.join();
        }
    }
}
