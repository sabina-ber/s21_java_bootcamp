import java.util.Scanner;

public class Program {
    public static final int terminator = 42;
    public static void main(String[] args) {
        int counter = 0;
        int number = 0;
        Scanner input = new Scanner(System.in);
        number = input.nextInt();
        if (number < 2) {
            System.err.println("Illegal argument");
            System.exit(-1);
        }
        while (number != terminator) {
            boolean isPrime = true;
            int sum = sumOfDigits(number);
            for (int i = 2; i * i <= sum; i++) {
                if (sum % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                counter++;
            }
            number = input.nextInt();
        }
        System.out.println("Count of coffee-request - " + counter);
    }
    private static int sumOfDigits(int number) {
        int sum = 0;
        while (number != 0) {
            sum += (number % 10);
            number /= 10;
        }
        return sum;
    }
}