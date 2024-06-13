package org.oop.nsem.budgettrackergui;

public class Other extends Budget{
    public final String type = "Other";
    public final String icon = "mdi2f-finance";

    public Other(String name, String period) {
        super(name, period);
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", super.getName(), super.getPeriod(), this.type, this.icon);
    }
}
