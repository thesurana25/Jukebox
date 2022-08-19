public class User {
    String userName;
    int userAge;
    String password;

    public User() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String userName, int userAge, String password) {
        this.userName = userName;
        this.userAge = userAge;
        this.password = password;
    }
}
