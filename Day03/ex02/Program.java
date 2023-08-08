public class Program {
    public static void main(String[] args) {
        try {
            int[] arguments = Checker.checkParam(args);
            Processor.processArguments(arguments);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.exit(-1);
        }
    }
}
