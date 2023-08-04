import java.io.File;

public class Checker {
    public static void checkParameterPath(String path) {
        if (path.startsWith("--current-folder=")) {
            String pathToDir = getFolderPath(path);
            File directory = new File(pathToDir);
            if (directory.isAbsolute()) {
                if (!directory.exists()) {
                    throw new IllegalArgumentException("Directory does not exist: " + pathToDir);
                }
            } else {
                throw new IllegalArgumentException("Путь не является абсолютным: " + pathToDir);
            }
        } else {
            throw new IllegalArgumentException("Path is required.");
        }
    }

    public static String checkCdCommand(String command) {
        String[] tokens = command.split("\\s+");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }
        String dirName = tokens[1];
        File directory = new File(dirName);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Directory not found: " + dirName);
        }
        return dirName;
    }

    public static String[] checkMvCommand(String command, String path) {
        String[] tokens = command.split("\\s+");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }
        String source = path + File.separator + tokens[1];
        String destination = tokens[2].equals("..") ? new File(path).getParent() + File.separator + new File(tokens[1]).getName() : path + File.separator + tokens[2];
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("Source file not found: " + source);
        }
        File destinationFile = new File(destination);
        if (destinationFile.exists()) {
            throw new IllegalArgumentException("Destination file already exists: " + destination);
        }
        tokens[1] = source;
        tokens[2] = destination;
        return tokens;
    }

    public static String getFolderPath(String text) {
        return text.substring("--current-folder=".length());
    }

}
