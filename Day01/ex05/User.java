public class User {
    private final Integer identifier;
    private String name;
    private Integer balance;
    private final TransactionsList transactionsByUser;

    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();;
        setName(name);
        setBalance(balance);
        transactionsByUser = new TransactionsLinkedList();
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance < 0 ? 0 : balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsByUser;
    }

    @Override
    public String toString() {
        return "User name = " + name +
                "\nidentifier = " + identifier +
                "\nbalance = " + balance;
    }
}