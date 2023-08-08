import java.util.Random;

public class Processor {
    public static void processArguments(int[] arguments) {
        int arraySize = arguments[0];
        int threadsCount = arguments[1];
        int[] arrayToCalculate = generateIntArray(arraySize);
        summarizeNoThreads(arrayToCalculate);
        try {
            summarizeWithThreads(arrayToCalculate, threadsCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void summarizeNoThreads(int[] arrayToCalculate) {
        long sum = 0;
        for (int j : arrayToCalculate) {
            sum += j;
        }
    }

    public static void summarizeWithThreads(int[] arrayToCalculate, int threadsCount) throws InterruptedException {
        long startTime = System.nanoTime();
        int length = arrayToCalculate.length;
        int step = length / threadsCount;
        long[] results = new long[threadsCount];
        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                int start = threadIndex * step;
                int end = (threadIndex == threadsCount - 1) ? length : start + step;
                long threadSum = 0;
                for (int j = start; j < end; j++) {
                    threadSum += arrayToCalculate[j];
                }
                System.out.println("Thread " + (threadIndex + 1) + ": from " + start + " to " + (end - 1) + " sum is " + threadSum);
                results[threadIndex] = threadSum;
            });

            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }
        long sum = 0;
        for (long partSum : results) {
            sum += partSum;
        }

        System.out.println("Sum by threads: " + sum);
    }


    private static int[] generateIntArray(int length) {
        int[] array = new int[length];
        int min = -1000;
        int max = 1000;
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt((max - min) + 1) + min;
        }
        return array;
    }
}


