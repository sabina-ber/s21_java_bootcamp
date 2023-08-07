public class Program {
    public static void main(String[] args) {
        try {
            int counts = Checker.checkParam(args);
            Hen hen = new Hen("Hen", counts);
            Egg egg = new Egg("Egg", counts);
            egg.t.start();
            hen.t.start();
            hen.t.join();
            egg.t.join();
            for (int i = 0; i < counts; i++) {
                System.out.println(Thread.currentThread() + "Human");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.exit(-1);
        }
    }
}
