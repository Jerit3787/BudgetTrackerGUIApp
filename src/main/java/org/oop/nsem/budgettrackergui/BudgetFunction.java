package org.oop.nsem.budgettrackergui;

import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class BudgetFunction {
    FileUtility util = new FileUtility();
    private Scanner fileScan;
    private String currentUserId;
    private ArrayList<Budget> budgetList = new ArrayList<Budget>();
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    public BudgetFunction(String id, ArrayList<Budget> budgetList) {
        this.currentUserId = id;
        this.budgetList = budgetList;
    }

    public ArrayList<String> loadBudgetName() {
        ArrayList<String> budget = new ArrayList<String>();

        Path path = Paths.get(util.getBudgetPath(currentUserId));
        try {
            fileScan = new Scanner(path);

            while (fileScan.hasNext()) {
                String data = fileScan.nextLine();
                System.out.println(data);
                String[] dataArray = data.split(",");

                budget.add(dataArray[0]);
            }
        } catch (Exception e) {
            System.out.printf("Message: %s", e);
        }

        return budget;
    }

    public Double[] checkTotal() {
        TransactionFunction trans = new TransactionFunction(currentUserId, transactionList);
        transactionList = trans.loadTransaction();
        budgetList = loadBudget();
        Double[] remainingList = new Double[budgetList.size()];
        int index = 0;

        for (Budget budget: budgetList) {
            remainingList[index] = 0.0;
            for (Transaction transactionData : transactionList) {
                if (budget.getName().equals(transactionData.getCategory())) {
                    System.out.println(budget.getName());
                    System.out.println(transactionData.getCategory());
                    try {
                        if (((Income) transactionData).getType().equals("Income")) {
                            remainingList[index] = remainingList[index] + transactionData.getAmount();
                            System.out.println(remainingList[index]);
                        }
                    } catch (Exception e) {
                        System.out.println("not income");
                    }
                }
            }
            index++;
        }

        return remainingList;
    }

    public Double[] checkRemaining() {
        TransactionFunction trans = new TransactionFunction(currentUserId, transactionList);
        transactionList = trans.loadTransaction();
        budgetList = loadBudget();
        Double[] remainingList = new Double[budgetList.size()];
        int index = 0;

        for (Budget budget: budgetList) {
            remainingList[index] = 0.0;
            for (Transaction transactionData : transactionList) {
                if (budget.getName().equals(transactionData.getCategory())) {
                    System.out.println(budget.getName());
                    System.out.println(transactionData.getCategory());
                    try {
                        if (((Income) transactionData).getType().equals("Income")) {
                            remainingList[index] = remainingList[index] + transactionData.getAmount();
                            System.out.println(remainingList[index]);
                        }
                    } catch (Exception e) {
                        System.out.println("not income");
                        if (((Expenses) transactionData).getType().equals("Expenses")) {
                            remainingList[index] = remainingList[index] - transactionData.getAmount();
                            System.out.println(remainingList[index]);
                        }
                    }
                }
            }
            index++;
        }

        return remainingList;
    }

    public void addBudget(String name, String period, String type) {
        Budget budget;
        switch (type) {
            case "Entertainment":
                budget = new Entertainment(name, period);
                break;

            case "Groceries":
                budget = new Groceries(name, period);
                break;

            case "Utility":
                budget = new Utility(name, period);
                break;

            default:
                budget = new Other(name, period);
                break;
        }

        budgetList.add(budget);
        util.saveBudgetFile(budget.toString(), currentUserId);
    }


    public ArrayList<Budget> loadBudget() {
        Budget budgetData;
        budgetList = new ArrayList<Budget>();
        Path path = Paths.get(util.getBudgetPath(currentUserId));
        try {
            fileScan = new Scanner(path);

            while (fileScan.hasNext()) {
                String data = fileScan.nextLine();
                System.out.println(data);
                String[] dataArray = data.split(",");

                switch (dataArray[2]) {
                    case "Entertainment":
                        System.out.println("Entertainment");
                        budgetData = new Entertainment(dataArray[0], dataArray[1]);
                        break;

                    case "Groceries":
                        budgetData = new Groceries(dataArray[0], dataArray[1]);
                        break;

                    case "Utility":
                        budgetData = new Utility(dataArray[0], dataArray[1]);
                        break;

                    default:
                        budgetData = new Other(dataArray[0], dataArray[1]);
                        break;
                }
                budgetList.add(budgetData);
            }
        } catch (Exception e) {
            System.out.printf("Message: %s", e);
        }

        return budgetList;
    }
}
