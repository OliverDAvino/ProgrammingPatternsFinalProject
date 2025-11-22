module com.example.pseudobank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pseudobank.View to javafx.fxml;
    exports com.example.pseudobank;
}