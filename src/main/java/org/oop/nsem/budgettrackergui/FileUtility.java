package org.oop.nsem.budgettrackergui;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileUtility {
    Scanner fileScanner;
    public enum budgetDuration { WEEKLY, MONTHLY };

    final String AUTH_PATH = "database\\authentication\\auth.txt";
    final String BUDGET_PATH = "database\\budget\\";
    final String TRANSACTION_PATH = "database\\transaction\\";

    public String getBudgetPath(String id) {
        return String.format("%s%s.txt", BUDGET_PATH, id);
    }

    public String getTransactionPath(String id) {
        return String.format("%s%s.txt", TRANSACTION_PATH, id);
    }

    public void saveAuthFile(String data) {
        Path file = Paths.get(AUTH_PATH);
        writeFile(file, data);
    }

    public void saveBudgetFile(String data, String id) {
        Path file = Paths.get(getBudgetPath(id));
        writeFile(file, data);
    }

    public void saveTransactionFile(String data, String id) {
        Path file = Paths.get(getTransactionPath(id));
        writeFile(file, data);
    }

    private void writeFile(Path filePath, String data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toString(), true))) {
            writer.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
}
