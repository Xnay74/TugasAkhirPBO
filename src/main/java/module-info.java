module com.example.pbotugasbesar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pbotugasbesar to javafx.fxml;
    exports com.example.pbotugasbesar;
    exports com.example.pbotugasbesar.data;
    opens com.example.pbotugasbesar.data to javafx.fxml;
}