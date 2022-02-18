module com.jhr2122.unit5.finalactivity {
    requires java.persistence;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires android.json;

    opens com.jhr2122.unit5.finalactivity to javafx.fxml;
    exports com.jhr2122.unit5.finalactivity;
}