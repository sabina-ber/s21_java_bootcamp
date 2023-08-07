public class Hen implements Runnable {
    String name;
    private final int count;
    Thread t;
    Hen(String threadName, int count) {
        name = threadName;
        this.count = count;
        t = new Thread(this, name);
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count; i++) {
            System.out.println(Thread.currentThread() + "Hen");
        }
    }
}
