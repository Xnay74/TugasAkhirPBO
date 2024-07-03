package com.example.pbotugasbesar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.pbotugasbesar.*;

public class Main extends Application {

    private Admin admin;
    private Student student;
    private VBox mainMenu;
    private StackPane stackPane;

    public static void main(String[] args) {
        user();
        launch(args);
    }

    public static void user(){
        StudentData student1 = new StudentData("Bimo","202310350311500","Teknik","Teknik Sipil");
        StudentData student2 = new StudentData("G","202310390311123","FIKES","Keperawatan");
        Admin.getStudents().add(student1);
        Admin.getStudents().add(student2);
        Book book1 = new Book(1,"Perang CR","History","SuperGuru",2020,3);
        Book book2 = new Book(2,"Geyming","Text","Bubu",2016,3);
        Admin.getBooks().add(book1);
        Admin.getBooks().add(book2);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        admin = new Admin();
        student = new Student(admin);

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

        Scene mainScene = new Scene(stackPane, 600, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        mainScene.setUserData(this);
    }

    public void showMainMenu() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(mainMenu);
    }

}
