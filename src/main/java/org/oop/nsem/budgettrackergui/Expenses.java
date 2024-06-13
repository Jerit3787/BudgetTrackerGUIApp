package org.oop.nsem.budgettrackergui;

public class Expenses extends Transaction{
    public final String type = "Expenses";

    public Expenses(String date, double amount, String category, String description) {
        super(date, amount, category, description);

    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s,%.2f,%s,%s,%s", super.getDate(), super.getAmount(), super.getCategory(), super.getDescription(), this.type);
    }
}
