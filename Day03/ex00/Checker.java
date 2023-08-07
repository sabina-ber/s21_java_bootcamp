public class Checker {
    public static int checkParam(String[] args) throws Exception {
            int repetitions = 0;
            if (args.length == 1) {
                if (args[0].startsWith("--count=")) {
                    try {
                        String[] parts = args[0].split("=");
                        repetitions = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        throw  new Exception("Invalid number format for --count argument");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new Exception("No value provided for --count argument");
                    }
                } else {
                    throw new Exception("Invalid argument. Expected format is --count=N");
                }
            } else {
                throw new Exception("Invalid number of arguments. Expected 1 argument");
            }
            return repetitions;
    }
}
