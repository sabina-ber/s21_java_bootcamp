public class Checker {
    public static int[] checkParam(String[] args) throws Exception {
        int[] repetitions = new int[2];
        if (args.length == 2) {
            repetitions[0] = checkFirstParam(args[0]);
            repetitions[1] = checkSecondParam(args[1]);
            if (repetitions[0] < repetitions[1]) {
                throw new Exception("Invalid argument. Expected first argument to be less than second");
            }
    }
        return repetitions;
}

    private static int checkFirstParam(String argument) throws Exception {
        int repetitions = 0;
        if (argument.startsWith("--arraySize=")) {
            try {
                String[] parts = argument.split("=");
                repetitions = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw  new Exception("Invalid number format for --count argument");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("No value provided for --count argument");
            }
        } else {
            throw new Exception("Invalid argument. Expected format is --count=N");
        }
        if (repetitions < 1) {
            throw new Exception("Invalid argument. Expected positive number");
        } else if (repetitions >= 2000000) {
            throw new Exception("Invalid argument. Expected positive number");
        }
        return repetitions;
    }

    private static int checkSecondParam(String argument) throws Exception {
        int repetitions = 0;
        if (argument.startsWith("--threadsCount=")) {
            try {
                String[] parts = argument.split("=");
                repetitions = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw  new Exception("Invalid number format for --count argument");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("No value provided for --count argument");
            }
        } else {
            throw new Exception("Invalid argument. Expected format is --count=N");
        }
        if ((repetitions < 1) || (repetitions >= 2000000)) {
            throw new Exception("Invalid argument. Expected positive number");
        }
        return repetitions;
    }
}
