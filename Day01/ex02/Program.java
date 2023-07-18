public class Program {
    public static void main(String[] args) {
        User recipient1 = new User("John", 600);
        User sender2 = new User("Steven", 400);
        User recipient3 = new User("Lucas", 366);
        User recipient4 = new User("John", 600);
        User sender5 = new User("Steven", 400);
        User recipient6 = new User("Lucas", 366);
        User recipient7 = new User("John", 680);
        User sender8 = new User("Steven", 400);
        User recipient9 = new User("Lucas", 366);
        User recipient10 = new User("John", 600);
        User sender11 = new User("Steven", 400);
        User recipient12 = new User("Lucas", 366);
        UsersArrayList usersList = new UsersArrayList();
        usersList.addUser(recipient1);
        usersList.addUser(sender2);
        usersList.addUser(recipient3);
        usersList.addUser(recipient4);
        usersList.addUser(sender5);
        usersList.addUser(recipient6);
        usersList.addUser(recipient7);
        usersList.addUser(sender8);
        usersList.addUser(recipient9);
        usersList.addUser(recipient10);
        usersList.addUser(sender11);
        usersList.addUser(recipient12);
        System.out.println("Size of list: " + usersList.getUsersListSize());
        System.out.println("Length of the array is " + usersList.getArrayLength());
        User temp = usersList.getUserById(5);
        System.out.println(temp.toString());
        System.out.println();
        User temp1 = usersList.getUserByIndex(2);
        System.out.println(temp1.toString());

        }
    }

