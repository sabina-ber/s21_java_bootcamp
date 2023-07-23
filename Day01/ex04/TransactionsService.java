import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();
    public void addUser(User user) {
        usersList.addUser(user);
    }
    public Integer getBalance(User user) {
        return usersList.getUserById(user.getIdentifier()).getBalance();
    }

    public void executeTransaction(Integer recipientId, Integer senderId, Integer amount) throws UserNotFoundException, NotEnoughMoneyException {
        User recipient = usersList.getUserById(recipientId);
        User sender = usersList.getUserById(senderId);
        if (!checkBalance(sender, amount)) {
            throw new NotEnoughMoneyException("Not enough money on the balance of the sender!");
        }
        UUID identifier = UUID.randomUUID();
        Transaction debitTransaction = new Transaction(recipient, sender, Transaction.TransferCategory.DEBIT, amount, identifier);
        Transaction creditTransaction = new Transaction(sender, recipient, Transaction.TransferCategory.CREDIT, -amount, identifier);
        recipient.getTransactionsList().addTransaction(debitTransaction);
        sender.getTransactionsList().addTransaction(creditTransaction);
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }
    public Transaction[] getTransactionsByUser(User user) { return user.getTransactionsList().toArray(); }
    public void removeTransactionById(User user, UUID id) throws TransactionNotFoundException {
        user.getTransactionsList().removeTransactionById(id);
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsList debitTransactions = new TransactionsLinkedList();
        TransactionsList creditTransactions = new TransactionsLinkedList();
        TransactionsList unpairedTransactions = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getUsersListSize(); i++) {
            User user = usersList.getUserByIndex(i);
            TransactionsList userTransactionsList = user.getTransactionsList();
            Transaction[] arrayTransactions = userTransactionsList.toArray();
            for (Transaction current : arrayTransactions) {
                if (current.getTransferCategory() == Transaction.TransferCategory.DEBIT) {
                    debitTransactions.addTransaction(current);
                } else {
                    creditTransactions.addTransaction(current);
                }
            }
        }
        Transaction[] debitArray = debitTransactions.toArray();
        Transaction[] creditArray = creditTransactions.toArray();
        for (Transaction debit : debitArray) {
            boolean isPaired = false;
            for (Transaction credit : creditArray) {
                if (debit.getIdentifier().equals(credit.getIdentifier())) {
                    isPaired = true;
                    break;
                }
            }
            if (!isPaired) {
                unpairedTransactions.addTransaction(debit);
            }
        }
        for (Transaction credit : creditArray) {
            boolean isPaired = false;
            for (Transaction debit : debitArray) {
                if (credit.getIdentifier().equals(debit.getIdentifier())) {
                    isPaired = true;
                    break;
                }
            }
            if (!isPaired) {
                unpairedTransactions.addTransaction(credit);
            }
        }

        return unpairedTransactions.toArray();
    }

//    public Transaction[] getUnpairedTransactions() {
//        TransactionsList allTransactions = new TransactionsLinkedList();
//
//        for (int i = 0; i < usersList.getUsersListSize(); i++) {
//            User user = usersList.getUserByIndex(i);
//            Transaction[] userTransactions = user.getTransactionsList().toArray();
//            for (Transaction transaction : userTransactions) {
//                allTransactions.addTransaction(transaction);
//            }
//        }
//
//        TransactionsList unpairedTransactions = new TransactionsLinkedList();
//        Transaction[] allTransactionsArray = allTransactions.toArray();
//
//        for (int i = 0; i < allTransactions.getSize(); i++) {
//            boolean isPaired = false;
//            for (int j = i + 1; j < allTransactions.getSize(); j++) {
//                if (allTransactionsArray[i].getIdentifier().equals(allTransactionsArray[j].getIdentifier())) {
//                    isPaired = true;
//                    break;
//                }
//            }
//            if (!isPaired) {
//                unpairedTransactions.addTransaction(allTransactionsArray[i]);
//            }
//        }
//
//        return unpairedTransactions.toArray();
//    }

    private boolean checkBalance(User user, Integer amount) {
        return user.getBalance() >= amount;
    }


}