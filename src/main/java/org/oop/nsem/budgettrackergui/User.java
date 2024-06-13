package org.oop.nsem.budgettrackergui;

public class User {
    private String username;
    private String password;
    public String displayName;

    // FOR ACCOUNT CREATION
    public User(String username, String password, String displayName){
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", this.username, this.password, this.displayName);
    }
}
