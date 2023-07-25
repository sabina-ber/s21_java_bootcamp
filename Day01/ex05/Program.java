public class Program {
    public static void main(String[]args) {
        boolean isDevMode = false;
        if (args.length > 0) {
            if (args[0].equals("--profile=dev"));
            isDevMode = true;
        }
        Menu menu = new Menu(isDevMode);
        menu.run();
    }
}