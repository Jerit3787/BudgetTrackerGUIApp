package org.oop.nsem.budgettrackergui;

public abstract class Budget {
    private String name;
    private String period;

    public Budget(String name, String period) {
        this.name = name;
        this.period = period;
    }

    public String getName() {
        return this.name;
    }

    public String getPeriod() {
        return period;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public abstract String toString();
}
