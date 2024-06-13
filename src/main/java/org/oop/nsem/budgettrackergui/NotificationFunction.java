package org.oop.nsem.budgettrackergui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class NotificationFunction {
    FileUtility util = new FileUtility();
    private Scanner fileScan;
    private String currentUserId;
    private ArrayList<Notification> notificationsList;

    public NotificationFunction(String id, ArrayList<Notification> notificationsList) {
        this.currentUserId = id;
        this.notificationsList = notificationsList;
    }

    public void addNotification(String date, String type, String message) {
        Notification notification = new Notification(date, type, message);
        notificationsList.add(notification);
        util.saveNotificationFile(notification.toString(), currentUserId);
    }

    public ArrayList<Notification> loadNotification() {
        notificationsList = new ArrayList<Notification>();
        Path path = Paths.get(util.getNotificationPath(currentUserId));
        try {
            fileScan = new Scanner(path);

            while (fileScan.hasNext()) {
                String data = fileScan.nextLine();
                System.out.println(data);
                String[] dataArray = data.split(",");
                notificationsList.add(new Notification(dataArray[0], dataArray[1], dataArray[2]));
            }
        } catch (Exception e) {
            System.out.printf("Message: %s", e);
        };

        return notificationsList;
    }
}
