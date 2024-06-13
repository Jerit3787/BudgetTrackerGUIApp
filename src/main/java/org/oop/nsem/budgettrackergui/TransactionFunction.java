package org.oop.nsem.budgettrackergui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TransactionFunction {
    FileUtility util = new FileUtility();
    private Scanner fileScan;
    private String currentUserId;
    private ArrayList<Transaction> transactionsList;

    public TransactionFunction(String id, ArrayList<Transaction> transactionsList) {
        this.currentUserId = id;
        this.transactionsList = transactionsList;
    }

    public void addTransaction(String date, double amount, String category, String type, String desc) {
        Transaction transaction;
        if (type.contains("Income")) {
            transaction = new Income(date, amount, category, desc);
        } else {
            transaction = new Expenses(date, amount, category, desc);
        }

        transactionsList.add(transaction);
        util.saveTransactionFile(transaction.toString(), currentUserId);
    }

    public ArrayList<Transaction> loadTransaction() {
        transactionsList = new ArrayList<Transaction>();
        Path path = Paths.get(util.getTransactionPath(currentUserId));
        try {
            fileScan = new Scanner(path);

            while (fileScan.hasNext()) {
                String data = fileScan.nextLine();
                System.out.println(data);
                String[] dataArray = data.split(",");

                if (dataArray[4].contains("Income")) {
                    transactionsList.add(new Income(dataArray[0], Double.parseDouble(dataArray[1]), dataArray[2], dataArray[3]));
                } else {
                    transactionsList.add(new Expenses(dataArray[0], Double.parseDouble(dataArray[1]), dataArray[2], dataArray[3]));
                }
            }
        } catch (Exception e) {
            System.out.printf("Message: %s", e);
        };

        return transactionsList;
    }
}
