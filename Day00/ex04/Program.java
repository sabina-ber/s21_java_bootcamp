import java.util.Scanner;

public class Program {
    public static final int MAX_HISTOGRAM_CHAR = 65536;
    public static final int MAX_HEIGHT = 10;
    public static final int TOP_TEN = 10;
    public static final int MAX_COUNTS = 999;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        char[] charArray = str.toCharArray();
        int[] counts = new int[MAX_HISTOGRAM_CHAR];
        for (char c : charArray) {
            counts[c]++;
        }

        char[] topChars = new char[TOP_TEN];
        int[] topCounts = new int[TOP_TEN];

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                for (int j = 0; j < topCounts.length; j++) {
                    if (counts[i] > topCounts[j] ||
                            (counts[i] == topCounts[j] && i < topChars[j])) {
                        for (int k = topCounts.length - 1; k > j; k--) {
                            topCounts[k] = topCounts[k - 1];
                            topChars[k] = topChars[k - 1];
                        }
                        topCounts[j] = counts[i];
                        topChars[j] = (char) i;
                        break;
                    }
                }
            }
        }

        int maxCount = topCounts[0];

        for (int i = 0; i < 10; i++) {
            if (topCounts[i] == maxCount) {
                System.out.print(topCounts[i] + "  ");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (topCounts[j] * 10 / maxCount >= i) {
                    System.out.print("#  ");
                } else if (topCounts[j] * 10 / maxCount == i - 1) {
                    if (topCounts[j] != 0) {
                        System.out.print(topCounts[j] + "  ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < TOP_TEN; i++) {
            System.out.print(topChars[i] + "  ");
        }
    }
}

