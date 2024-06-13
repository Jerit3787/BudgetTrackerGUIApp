package org.oop.nsem.budgettrackergui;

public class Entertainment extends Budget{
    public final String type = "Entertainment";
    public final String icon = "mdi2g-gamepad-variant-outline";

    public Entertainment(String name, String period) {
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
