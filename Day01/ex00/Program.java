public class Program {
    public static void main(String[] args) {

        User sender = new User("John", 1000);
        User recipient = new User("Alice", 500);

        Transaction transaction = new Transaction(recipient, sender, Transaction.TransferCategory.DEBIT, 200);

        System.out.println("Transaction ID: " + transaction.getIdentifier());
        System.out.println("Sender: " + transaction.getSender().getName());
        System.out.println("Recipient: " + transaction.getRecipient().getName());
        System.out.println("Transfer Category: " + transaction.getTransferCategory());
        System.out.println("Transfer Amount: " + transaction.getTransferAmount());

        sender.setBalance(sender.getBalance() - transaction.getTransferAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getTransferAmount());

        System.out.println("Sender's New Balance: " + sender.getBalance());
        System.out.println("Recipient's New Balance: " + recipient.getBalance());
    }
}
