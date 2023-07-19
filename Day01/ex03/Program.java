import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        int bal = 100;
        String name = "User";
        TransactionsLinkedList list = new TransactionsLinkedList();

        for (int i = 0; i < 10; i++) {
            User sender = new User(name + i, bal);
            User recipient = new User(name + (i + 1), bal);
            Transaction transaction = new Transaction(sender, recipient, Transaction.TransferCategory.DEBIT, 100);
            list.addTransaction(transaction);
            bal += 100;
        }

        for (int i = 0; i < 10; i++) {
            User sender = new User(name + i, bal);
            User recipient = new User(name + (i + 1), bal);
            Transaction transaction = new Transaction(sender, recipient, Transaction.TransferCategory.CREDIT, -100);
            list.addTransaction(transaction);
            bal += 100;
        }

        int size = list.getSize();
        System.out.println("Size: " + size);

        System.out.println("Invalid transaction ID removal: ");
        try {
            list.removeTransactionById(UUID.randomUUID());
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
        }

        System.out.println("List to array test: ");
        Transaction[] arrayOfTransaction = list.toArray();
        int length = arrayOfTransaction.length;
        System.out.println("Length of the array: " + length);

        for (Transaction item : arrayOfTransaction) {
            item.printTransactionInfo();
        }
    }
}