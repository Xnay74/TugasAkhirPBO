package com.example.pbotugasbesar;

import com.example.pbotugasbesar.data.Admin;
import com.example.pbotugasbesar.data.Student;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final Admin admin = new Admin();
    private final Student student = new Student(admin);
    private VBox mainMenu;
    private Scene mainScene;
    private StackPane stackPane;

    public static void main(String[] args) {

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Button studentLoginBtn = new Button("Login as Student");
        studentLoginBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            student.displayStudentLogin(stackPane);
        });

        Button adminLoginBtn = new Button("Login as Admin");
        adminLoginBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            admin.displayAdminLogin(stackPane);
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Goodbye");
            alert.setHeaderText(null);
            alert.setContentText("Thank you for using the Library Management System!");
            alert.showAndWait();
            primaryStage.close();
        });

        mainMenu = new VBox(10);
        mainMenu.getChildren().addAll(studentLoginBtn, adminLoginBtn, exitBtn);
        mainMenu.setStyle("-fx-padding: 20; -fx-alignment: center;");

        stackPane = new StackPane();
        stackPane.getChildren().add(mainMenu);

        mainScene = new Scene(stackPane, 600, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        mainScene.setUserData(this);
    }

    public void showMainMenu() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(mainMenu);
    }
}
