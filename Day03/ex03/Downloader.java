import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;

public class Downloader implements Runnable {

    private final int fileId;
    private final String fileUrl;
    private static final String DOWNLOAD_DIR = "downloads";

    public Downloader(int fileId, String fileUrl) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
    }

    @Override
    public void run() {
        try {
            // Ensure the download directory exists
            Files.createDirectories(Paths.get(DOWNLOAD_DIR));

            URL url = new URL(fileUrl);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            String fileName = Paths.get(url.getPath()).getFileName().toString();

            // Change the path to save inside the "downloads" directory
            Files.write(Paths.get(DOWNLOAD_DIR, fileName), url.openStream().readAllBytes(), StandardOpenOption.CREATE);

            System.out.println(Thread.currentThread().getName() + " finished download file number " + fileId);
        } catch (Exception e) {
            System.out.println("Error downloading file number " + fileId + ": " + e.getMessage());
        }
    }
}

