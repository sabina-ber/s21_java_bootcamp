interface UsersList {
    void addUser(User newUser);
    User getUserById(int id) throws UserNotFoundException;
    User getUserByIndex(int index) throws UserNotFoundException;
    int  getUsersListSize();
}