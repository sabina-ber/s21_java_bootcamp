public class Program {
    public static void main(String[] args) {

        User sender = new User("John", 1000);
        User recipient2 = new User("Alice", 500);
        User recipient1 = new User("Alice", 500);
        User recipient = new User("Alice", 500);

        System.out.println("Sender's New Balance: " + sender.getBalance());
        System.out.println("Recipient's New Balance: " + recipient.getBalance());
        System.out.println("Last id: " + sender.getIdentifier());
        System.out.println("Last id: " + recipient2.getIdentifier());
        System.out.println("Last id: " + UserIdsGenerator.getInstance().generateId());
        System.out.println("Last id: " + recipient1.getIdentifier());
        System.out.println("Last id: " + recipient.getIdentifier());
        User recipient3 = new User("Alice", 500);
        System.out.println("Last id: " + recipient3.getIdentifier());

    }
}
