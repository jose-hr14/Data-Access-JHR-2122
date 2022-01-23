module com.jhr2122.unit5.finalactivity {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires java.sql;


    opens com.jhr2122.unit5.finalactivity to javafx.fxml;
    exports com.jhr2122.unit5.finalactivity;
}