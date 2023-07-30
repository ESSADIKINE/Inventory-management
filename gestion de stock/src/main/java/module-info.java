module gestion.de.stock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    exports gestion.de.stock;
    opens gestion.de.stock to javafx.fxml;
}
