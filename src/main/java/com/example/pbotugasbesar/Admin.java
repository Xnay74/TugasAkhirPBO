package com.example.pbotugasbesar;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Admin  {

    private List<StudentData> students = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private VBox adminMenu;
    private int bookIdCounter = 1;

    public void displayAdminLogin(StackPane stackPane) {
        GridPane grid = createFormPane();

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginBtn = new Button("Login");
        GridPane.setConstraints(loginBtn, 1, 2);
        loginBtn.setOnAction(e -> {
            if ("admin".equals(usernameField.getText()) && "admin".equals(passwordField.getText())) {
                stackPane.getChildren().clear();
                displayAdminMenu(stackPane);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 0, 2);
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            Main mainApp = (Main) stackPane.getScene().getUserData();
            if (mainApp != null) {
                mainApp.showMainMenu();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to return to main menu.");
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginBtn, backBtn);
        stackPane.getChildren().add(grid);
    }

    private void displayAdminMenu(StackPane stackPane) {
        adminMenu = new VBox(10);
        adminMenu.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Button addStudentBtn = new Button("Add Student");
        addStudentBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            addStudent(stackPane);
        });

        Button addBookBtn = new Button("Add Book");
        addBookBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            addBook(stackPane);
        });

        Button displayStudentsBtn = new Button("Display Students");
        displayStudentsBtn.setOnAction(e -> displayStudents(stackPane));

        Button displayBooksBtn = new Button("Display Books");
        displayBooksBtn.setOnAction(e -> displayBooks(stackPane));

        Button logoutBtn = new Button("Back");
        logoutBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            Main mainApp = (Main) stackPane.getScene().getUserData();
            if (mainApp != null) {
                mainApp.showMainMenu();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to return to main menu.");
            }
        });

        adminMenu.getChildren().addAll(addStudentBtn, addBookBtn, displayStudentsBtn, displayBooksBtn, logoutBtn);
        stackPane.getChildren().add(adminMenu);
    }

    private void addStudent(StackPane stackPane) {
        GridPane grid = createFormPane();

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 0);

        Label nimLabel = new Label("NIM:");
        GridPane.setConstraints(nimLabel, 0, 1);
        TextField nimField = new TextField();
        GridPane.setConstraints(nimField, 1, 1);

        Label facultyLabel = new Label("Faculty:");
        GridPane.setConstraints(facultyLabel, 0, 2);
        TextField facultyField = new TextField();
        GridPane.setConstraints(facultyField, 1, 2);

        Label departmentLabel = new Label("Department:");
        GridPane.setConstraints(departmentLabel, 0, 3);
        TextField departmentField = new TextField();
        GridPane.setConstraints(departmentField, 1, 3);

        Button addBtn = new Button("Add");
        GridPane.setConstraints(addBtn, 1, 4);
        addBtn.setOnAction(e -> {
            if (nimField.getText().matches("\\d{15}")) {
                students.add(new StudentData(nameField.getText(), nimField.getText(), facultyField.getText(), departmentField.getText()));
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");
                stackPane.getChildren().clear();
                displayAdminMenu(stackPane);
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid NIM", "NIM should be 15 digits.");
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 0, 4);
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        grid.getChildren().addAll(nameLabel, nameField, nimLabel, nimField, facultyLabel, facultyField, departmentLabel, departmentField, addBtn, backBtn);
        stackPane.getChildren().add(grid);
    }

    private void addBook(StackPane stackPane) {
        GridPane grid = createFormPane();

        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 0);
        TextField titleField = new TextField();
        GridPane.setConstraints(titleField, 1, 0);

        Label genreLabel = new Label("Genre:");
        GridPane.setConstraints(genreLabel, 0, 1);
        ComboBox<String> genreBox = new ComboBox<>();
        genreBox.getItems().addAll("Story", "History", "Text", "Encyclopedia", "Biography", "Fiction");
        GridPane.setConstraints(genreBox, 1, 1);

        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 2);
        TextField authorField = new TextField();
        GridPane.setConstraints(authorField, 1, 2);

        Label yearLabel = new Label("Year:");
        GridPane.setConstraints(yearLabel, 0, 3);
        TextField yearField = new TextField();
        GridPane.setConstraints(yearField, 1, 3);

        Label stockLabel = new Label("Stock:");
        GridPane.setConstraints(stockLabel, 0, 4);
        TextField stockField = new TextField();
        GridPane.setConstraints(stockField, 1, 4);

        Button addBtn = new Button("Add");
        GridPane.setConstraints(addBtn, 1, 5);
        addBtn.setOnAction(e -> {
            try {
                int year = Integer.parseInt(yearField.getText());
                int stock = Integer.parseInt(stockField.getText());
                books.add(new Book(bookIdCounter++, titleField.getText(), genreBox.getValue(), authorField.getText(), year, stock));
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully.");
                stackPane.getChildren().clear();
                displayAdminMenu(stackPane);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Year and Stock should be numeric.");
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 0, 5);
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        grid.getChildren().addAll(titleLabel, titleField, genreLabel, genreBox, authorLabel, authorField, yearLabel, yearField, stockLabel, stockField, addBtn, backBtn);
        stackPane.getChildren().add(grid);
    }

    private void displayStudents(StackPane stackPane) {
        TableView<StudentData> table = new TableView<>();
        table.setItems(FXCollections.observableList(students));

        TableColumn<StudentData, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<StudentData, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<StudentData, String> facultyColumn = new TableColumn<>("faculty");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        TableColumn<StudentData, String> departmentColumn = new TableColumn<>("department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        table.getColumns().addAll(nameColumn, nimColumn, facultyColumn, departmentColumn);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, backBtn);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private void displayBooks(StackPane stackPane) {
        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableList(books));

        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn, stockColumn);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, backBtn);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private GridPane createFormPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<StudentData> getStudents() {
        return students;
    }
}
