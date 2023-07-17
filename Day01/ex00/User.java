import java.util.UUID;

public class User {
    private final UUID identifier;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.identifier = UUID.randomUUID();
        setName(name);
        setBalance(balance);
    }

    public UUID getIdentifier() {
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
}


