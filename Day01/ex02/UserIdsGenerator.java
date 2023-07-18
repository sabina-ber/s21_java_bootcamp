public class UserIdsGenerator {
    private int lastGeneratedId = 0;

    private UserIdsGenerator() {}

    private static class UserIdsGeneratorHelper {
        private static final UserIdsGenerator instance = new UserIdsGenerator();
    }

    public static UserIdsGenerator getInstance() {
        return UserIdsGeneratorHelper.instance;
    }

    public synchronized Integer generateId() {
        lastGeneratedId++;
        return lastGeneratedId;
    }
}