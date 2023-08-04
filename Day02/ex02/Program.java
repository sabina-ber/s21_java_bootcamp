import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Path is required.");
            }
            String path = args[0];
            Checker.checkParameterPath(path);
            String currentFolder = Checker.getFolderPath(path);
            FileManager fileManager = FileManager.getInstance(currentFolder);
            Scanner scanner = fileManager.getScanner();
            String command;
            do {
                command = scanner.nextLine();
                fileManager.checkCommand(command);
            } while (!command.equals("exit"));

            scanner.close();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

