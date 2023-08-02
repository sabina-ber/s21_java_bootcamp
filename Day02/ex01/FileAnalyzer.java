import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.Collections;

public class FileAnalyzer {
    public static void process(String filePath1, String filePath2) throws IllegalArgumentException {
        checkPath(filePath1);
        checkPath(filePath2);
        Set<String> uniqueValues = new HashSet<>();
        writeUniqueValues(filePath1, uniqueValues);
        writeUniqueValues(filePath2, uniqueValues);
        writeInFile(uniqueValues);
        Vector<Integer> A = new Vector<>(Collections.nCopies(uniqueValues.size(), 0));
        Vector<Integer> B = new Vector<>(Collections.nCopies(uniqueValues.size(), 0));
        writeInVector(A, filePath1, uniqueValues);
        writeInVector(B, filePath2, uniqueValues);
        findSimilarity(A, B);
    }

    private static void writeInVector(Vector<Integer> A, String filePath, Set<String> uniqueValues) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                if (uniqueValues.contains(line)) {
                    int index = getIndex(line, uniqueValues);
                    addInVector(A, index);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addInVector(Vector<Integer> A, int index) {
        if (index < 0 || index >= A.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        int element = A.get(index);
        A.set(index, element + 1);
    }

    private static int getIndex(String line, Set<String> uniqueValues) {
        int index = 0;
        for (String value : uniqueValues) {
            if (line.equals(value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static void writeInFile(Set<String> uniqueValues) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (String value : uniqueValues) {
                writer.write(value);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeUniqueValues(String filePath, Set<String> uniqueValues) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                uniqueValues.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkPath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }
        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty.");
        }
        if(!filePath.endsWith(".txt")) {
            throw new IllegalArgumentException("File must be txt.");
        }
    }

    private static void findSimilarity(Vector<Integer> A, Vector<Integer> B) {
        int numerator = 0;
        double denominatorA = 0;
        double denominatorB = 0;
        double similarity = 0;
        for (int i = 0; i < A.size(); i++) {
            numerator += A.get(i) * B.get(i);
            denominatorA += A.get(i) * A.get(i);
            denominatorB += B.get(i) * B.get(i);
        }
        similarity = numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Similarity = " + df.format(similarity));
    }
}
