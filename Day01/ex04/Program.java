public class Program {
    public static void main(String[] args) {
        TransactionsService newService = new TransactionsService();
        User user1 = new User("John", 2000);
        User user2 = new User("Simon", 6000);
        User user3 = new User("Sue", 6000);
        User user4 = new User("Jack", 6000);

        newService.addUser(user1);
        newService.addUser(user2);
        newService.addUser(user3);
        newService.addUser(user4);
        newService.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 300);
        newService.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 300);
        newService.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 300);

        Transaction[] user1Transactions = newService.getTransactionsByUser(user1);
        for (Transaction i : user1Transactions) {
            i.printTransactionInfo();
        }
        System.out.println(user1.getBalance());
        System.out.println(user2.getBalance());

        newService.removeTransactionById(user1, user1Transactions[0].getIdentifier());
        user1Transactions = newService.getTransactionsByUser(user1);
        for (Transaction i : user1Transactions) {
            i.printTransactionInfo();
        }

    }
}
