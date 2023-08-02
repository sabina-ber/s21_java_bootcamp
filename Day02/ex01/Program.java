public class Program {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Two arguments are required.");
            }
            String filePath1 = args[0];
            String filePath2 = args[1];
            FileAnalyzer.process(filePath1, filePath2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
