import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FileManager {
    private static FileManager instance;
    private String path;
    private final Scanner scanner;

    private FileManager(String path) {
        this.path = path;
        this.scanner = new Scanner(System.in);
    }

    public static FileManager getInstance(String path) {
        if (instance == null) {
            instance = new FileManager(path);
        }
        return instance;
    }

    public String getPath() {
        return new File(path).getAbsolutePath();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void checkCommand(String command) {
        try {
            if (command.startsWith("cd ")) {
                String dirName = Checker.checkCdCommand(command);
                executeCd(dirName);
            }

            if (command.startsWith("ls")) {
                executeLs();
            }

            if (command.startsWith("mv ")) {
                String[] tokens = Checker.checkMvCommand(command, path);
                String source = tokens[1];
                String destination = tokens[2];
                executeMv(source, destination);
            }

            if (command.startsWith("exit")) {
                System.out.println("exit");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private void executeLs() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("ls");
            processBuilder.directory(new File(path));
            Process process = processBuilder.start();
            printProcessOutput(process);
        } catch (IOException e) {
            System.out.println("Error executing ls command: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeCd(String dirName) {
        File newDirectory = new File(path, dirName);
        if (!newDirectory.exists()) {
            System.out.println("Directory not found: " + newDirectory.getAbsolutePath());
        } else if (!newDirectory.isDirectory()) {
            System.out.println("Not a directory: " + newDirectory.getAbsolutePath());
        } else {
            path = newDirectory.getAbsolutePath();
            System.out.println("Current directory is now: " + path);
        }
    }

    private void executeMv(String source, String destination) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("mv", source, destination);
            processBuilder.directory(new File(path));
            Process process = processBuilder.start();
            printProcessOutput(process);
        } catch (IOException e) {
            System.out.println("Error executing mv command: " + e.getMessage());
        }
    }

    private void printProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

}

