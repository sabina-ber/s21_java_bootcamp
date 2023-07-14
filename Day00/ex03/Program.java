import java.util.Scanner;

public class Program {
    public static final String terminator = "42";
    public static final String illegal_argument = "Illegal Argument";
    public static final String week = "Week ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputW = scanner.nextLine();
        int weekNumber = 1;
        int grade = 0;
        long finalGrades = 0;
        StringBuilder sb = new StringBuilder();

        while (!inputW.equals(terminator) && (weekNumber < 18)) {
            if (inputW.equals(week + weekNumber)) {
                weekNumber++;
                grade = processGrade(scanner);
                finalGrades = finalGrades * 10 + grade;
                inputW = scanner.nextLine();
            } else {
                System.err.println(illegal_argument);
                System.exit(1);
            }
        }
        long reversedNumber = reverseNumber(finalGrades);
        for (int i = 1; i < weekNumber; i++) {
            sb.append(week).append(i).append(" ");
            int gradePrint = (int) (reversedNumber % 10);
            printGraph(sb, gradePrint);
            reversedNumber = reversedNumber / 10;
        }
        System.out.println(sb.toString());
        scanner.close();
    }

    private static int processGrade(Scanner scanner) {
        int min = 10;
        if (!scanner.hasNextLine()) {
            System.err.println(illegal_argument);
            System.exit(1);
        }
        Scanner lineScanner = new Scanner(scanner.nextLine());
        for (int i = 0; i < 5; i++) {
            if (!lineScanner.hasNextInt()) {
                System.err.println(illegal_argument);
                System.exit(1);
            }
            int grade = lineScanner.nextInt();
            if (isGradeValid(grade)) {
                if (grade < min) {
                    min = grade;
                }
            } else {
                System.err.println(illegal_argument);
                System.exit(1);
            }
        }
        lineScanner.close();
        return min;
    }

    private static boolean isGradeValid(int grade) {
        return grade > 0 && grade <= 9;
    }

    private static void printGraph(StringBuilder sb, int finalGrades) {
        for (int i = 0; i < finalGrades; i++) {
            sb.append("=");
        }
        sb.append(">").append("\n");
    }

    private static long reverseNumber(long number) {
        long reversedNumber = 0;
        while (number != 0) {
            reversedNumber = reversedNumber * 10 + number % 10;
            number /= 10;
        }
        return reversedNumber;
    }

}