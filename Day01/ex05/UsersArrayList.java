public class UsersArrayList implements UsersList {
    private final int defaultSize = 10;
    private final double extendSize = 1.5F;
    int size = 0;
    private User[] users = new User[defaultSize];

    @Override
    public void addUser(User newUser) {
        if (size == users.length) {
            User[] newUsers = new User[(int)(users.length * extendSize)];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            this.users = newUsers;
            this.users[size++] = newUser;
        } else {
            this.users[size++] = newUser;
        }
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < this.size; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found!");
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index < this.size && index >= 0) {
                return users[index];
            }
        throw new UserNotFoundException("User with index " + index + " not found!");
    }

    @Override
    public int getUsersListSize() {
        return size;
    }

    public int getArrayLength() {
        return users.length;
    }
}