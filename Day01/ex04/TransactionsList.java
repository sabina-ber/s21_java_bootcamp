import java.util.UUID;
public interface TransactionsList {
    void addTransaction(Transaction transaction);
    void removeTransactionById(UUID id) throws TransactionNotFoundException;
    Transaction[] toArray();
    int getSize();
}
