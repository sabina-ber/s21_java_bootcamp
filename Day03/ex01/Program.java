public class Program {
    static String current = "Egg";
    public static void main(String[] args) {

        try {
            int counts = Checker.checkParam(args);
            Hen hen = new Hen("Hen", counts);
            Egg egg = new Egg("Egg", counts);
            egg.t.start();
            hen.t.start();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.exit(-1);
        }
    }

    static void printAndNotify(String threadName, String nextThreadName) {
        synchronized (Program.class) {
            while (!Program.current.equals(threadName)) {
                try {
                    Program.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(threadName);
            Program.current = nextThreadName;
            Program.class.notifyAll();
        }
    }
}
