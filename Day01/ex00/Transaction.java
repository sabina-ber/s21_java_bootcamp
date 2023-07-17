import java.util.UUID;
public class Transaction {
    public enum TransferCategory {
        DEBIT,
        CREDIT
    }
    private final UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private Integer transferAmount;

    public Transaction(User recipient, User sender, TransferCategory transferCategory, Integer transferAmount) {
        this.identifier = UUID.randomUUID();
        setRecipient(recipient);
        setSender(sender);
        setTransferCategory(transferCategory);
        setTransferAmount(transferAmount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        this.transferCategory = transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        if (transferCategory == TransferCategory.DEBIT && transferAmount < 0) {
            transferAmount = 0;
        } else if (transferCategory == TransferCategory.CREDIT && transferAmount > 0) {
            transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
        }
    }
}
