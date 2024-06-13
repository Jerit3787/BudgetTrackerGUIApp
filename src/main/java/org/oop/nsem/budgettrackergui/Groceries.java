package org.oop.nsem.budgettrackergui;

public class Groceries extends Budget {
    public final String type = "Groceries";
    public final String icon = "mdi2s-shopping-outline";

    public Groceries(String name, String period) {
        super(name, period);
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", super.getName(), super.getPeriod(), this.type, this.icon);
    }
}
