import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
   private Node head;
   private Node tail;
   private int size;
   private static class Node {
       private Transaction transaction;
       private Node next;
       private Node prev;

       public Node(Transaction transaction) {
           this.transaction = transaction;
           this.next = null;
           this.prev = null;
       }
   }

    public TransactionsLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void addTransaction(Transaction transaction) {
       Node newnode = new Node(transaction);
         if (head == null) {
              head = newnode;
              tail = newnode;
         }
         else {
              tail.next = newnode;
              newnode.prev = tail;
              tail = newnode;
         }
        size++;
    }

    @Override
    public Transaction removeTransactionById(UUID id) {
        if (id == null) {
            throw new TransactionNotFoundException("Transaction with 'null' UUID can't be removed");
        }
        Node current = head;
        Transaction removedTransaction = null;
        boolean found = false;
        while (current != null) {
            if (current.transaction.getIdentifier().equals(id)) {
                found = true;
                removedTransaction = current.transaction;
                if (current.prev == null) {
                    head = current.next;
                    head.prev = null;
                }
                else if (current.next == null) {
                    tail = current.prev;
                    tail.next = null;
                }
                else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                break;
            }
            current = current.next;
        }
        if (!found) {
            throw new TransactionNotFoundException("Transaction with UUID " + id + " not found");
        }
        return removedTransaction;
    }

    @Override
    public Transaction[] toArray() {
        if (size > 0) {
            Transaction[] transactions = new Transaction[size];
            Node current = head;
            int i = 0;
            while (current != null) {
                transactions[i] = current.transaction;
                current = current.next;
                i++;
            }
            return transactions;
        }
        else {
            return null;
        }
    }

    @Override
    public int getSize() {
        return size;
    }
}
