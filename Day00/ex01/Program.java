import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number  <= 1) {
            System.err.println("Illegal argument");
            System.exit(-1);
        }
        int iterations = 1;
        boolean isPrime = true;
        for (int i = 2; i * i <= number; i++, iterations++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println(isPrime ? "true " + iterations: "false " + iterations);
    }
}