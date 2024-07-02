module com.example.pbotugasbesar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pbotugasbesar to javafx.fxml;
    exports com.example.pbotugasbesar;
}