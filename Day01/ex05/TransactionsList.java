import java.util.UUID;
public interface TransactionsList {
    void addTransaction(Transaction transaction);
    Transaction removeTransactionById(UUID id) throws TransactionNotFoundException;
    Transaction[] toArray();
    int getSize();
}
