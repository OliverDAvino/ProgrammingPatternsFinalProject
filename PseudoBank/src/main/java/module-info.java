module com.example.pseudobank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    //requires com.example.pseudobank;

    opens com.example.pseudobank to javafx.fxml;

    opens com.example.pseudobank.View to javafx.fxml;


    exports com.example.pseudobank;
}