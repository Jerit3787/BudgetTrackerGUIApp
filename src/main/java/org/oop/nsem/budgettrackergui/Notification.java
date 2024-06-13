package org.oop.nsem.budgettrackergui;

public class Notification {
    private String date;
    private String type;
    private String message;

    Notification(String date, String type, String message) {
        this.date = date;
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
