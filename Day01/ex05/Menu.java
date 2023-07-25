import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final boolean ifDevMode;
    private final TransactionsService transactionService;
    private final Scanner scanner;
    Menu(boolean ifDevMode) {
        this.ifDevMode = ifDevMode;
        transactionService = new TransactionsService();
        scanner = new Scanner(System.in);
    }
    public void run() {
        while (true) {
            if (ifDevMode) {
                printDevMessage();
                executeDevCommand();
            } else {
                printDefMessage();
                executeDefCommand();
            }
        }
    }

    private void printDevMessage() {
        System.out.println("1. Add a user");
        System.out.println("2. Show user balance");
        System.out.println("3. Perform a transaction");
        System.out.println("4. Show all transactions for a user");
        System.out.println("5. DEV – remove a transaction by ID");
        System.out.println("6. DEV – check transaction validity");
        System.out.println("7. Finish execution");
    }

    private void printDefMessage() {
        System.out.println("1. Add a user");
        System.out.println("2. Show user balance");
        System.out.println("3. Perform a transaction");
        System.out.println("4. Show all transactions for a user");
        System.out.println("5. Finish execution");
    }

    private void executeDevCommand() {
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> showBalance();
                case 3 -> addTransaction();
                case 4 -> showTransactions();
                case 5 -> removeTransaction();
                case 6 -> checkTransaction();
                case 7 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        } else {
            scanner.nextLine();
            System.out.println("Invalid choice");
        }
    }

    private void executeDefCommand() {
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> showBalance();
                case 3 -> addTransaction();
                case 4 -> showTransactions();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        } else {
            scanner.nextLine();
            System.out.println("Invalid choice");
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance: ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            String userName = parts[0];
            try {
                int balance = Integer.parseInt(parts[1]);
                User user = new User(userName, balance);
                transactionService.addUser(user);
                System.out.println("User with id = " + user.getIdentifier() + " was added.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        } else {
            System.out.println("Invalid input format. Please enter the user name and balance separated by a space.");
        }
    }

    private void showBalance() {
        System.out.println("Enter a user ID: ");
        try {
            int id = scanner.nextInt();
            System.out.println(transactionService.getUserNameById(id) + " " +
                    transactionService.getBalance(transactionService.getUserById(id)));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid user ID (integer value expected).");
            scanner.nextLine();
        }
    }


    private void addTransaction() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount:");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            try {
                int sender = Integer.parseInt(parts[0]);
                int recipient = Integer.parseInt(parts[1]);
                int amount = Integer.parseInt(parts[2]);
                transactionService.executeTransaction(recipient, sender, amount);
                System.out.println("The transfer is completed.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. All IDs and the transfer amount must be valid numbers.");
            }
        } else {
            System.out.println("Invalid input format. Please enter the user name and balance separated by a space.");
        }
    }

    private void showTransactions() {
        System.out.println("Enter a user ID");
        int id = scanner.nextInt();
        try {
            Transaction[] transactions = transactionService.getTransactionsByUser(transactionService.getUserById(id));
            if (transactions == null || transactions.length == 0) {
                throw new TransactionNotFoundException("No transactions found");
            }
            for (Transaction transaction : transactions) {
                System.out.print("To " + transaction.getRecipient().getName() + "(id = " +
                        transaction.getRecipient().getIdentifier() + ") ");
                System.out.print(transaction.getTransferAmount() + " ");
                System.out.println("with id = " + transaction.getIdentifier() + " ");
            }
        } catch (UserNotFoundException e) {
            System.out.println("User not found");
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeTransaction() {
        System.out.println("Enter a user ID and a transfer ID");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            int userId = Integer.parseInt(parts[0]);
            String uuid = (parts[1]);
            try {
                UUID transferId = UUID.fromString(uuid);
                Transaction toRemove = transactionService.removeTransactionById(transactionService.getUserById(userId), transferId);
                System.out.println("Transfer to " + toRemove.getRecipient().getName() + "(id = " +
                        toRemove.getRecipient().getIdentifier() + ")" + toRemove.getTransferAmount() +
                        " was removed.");
            } catch (TransactionNotFoundException | UserNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transfer ID. It should be a UUID.");
            }
        } else {
            System.out.println("Invalid input format. Please enter the user name and balance separated by a space.");
        }
    }

    public void checkTransaction() {
        System.out.println("Check results:");
        Transaction[] unpaired = transactionService.getUnpairedTransactions();
        if (unpaired.length == 0) {
            System.out.println("All transactions are paired.");
        } else {
            for (Transaction transaction : unpaired) {
                System.out.println(transaction.getRecipient().getName() +
                        " (id = " + transaction.getRecipient().getIdentifier() + ") " +
                        "has an unacknowledged transfer id = " +
                        transaction.getIdentifier() + " from " +
                        transaction.getSender().getName() + " (id = " +
                        transaction.getSender().getIdentifier() + ")" +
                        " for " + transaction.getTransferAmount());
            }
        }

    }

}
