module org.oop.nsem.budgettrackergui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens org.oop.nsem.budgettrackergui to javafx.fxml;
    exports org.oop.nsem.budgettrackergui;
}