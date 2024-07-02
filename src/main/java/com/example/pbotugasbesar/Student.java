package com.example.pbotugasbesar;


import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student  {

    private Admin admin;
    private StudentData loggedInStudent;
    private Map<String, List<Book>> borrowedBooks = new HashMap<>();

    public Student(Admin admin) {
        this.admin = admin;
    }

    public void displayStudentLogin(StackPane stackPane) {
        GridPane grid = createFormPane();

        Label nimLabel = new Label("NIM:");
        GridPane.setConstraints(nimLabel, 0, 0);
        TextField nimField = new TextField();
        GridPane.setConstraints(nimField, 1, 0);

        Button loginBtn = new Button("Login");
        GridPane.setConstraints(loginBtn, 1, 1);
        loginBtn.setOnAction(e -> {
            String nim = nimField.getText();
            if (nim.matches("\\d{15}")) {
                loggedInStudent = admin.getStudents().stream().filter(s -> s.getNim().equals(nim)).findFirst().orElse(null);
                if (loggedInStudent != null) {
                    stackPane.getChildren().clear();
                    displayStudentMenu(stackPane);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Student not found.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid NIM", "NIM should be 15 digits.");
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 0, 1);
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            Main mainApp = (Main) stackPane.getScene().getUserData();
            if (mainApp != null) {
                mainApp.showMainMenu();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to return to main menu.");
            }
        });

        grid.getChildren().addAll(nimLabel, nimField, loginBtn, backBtn);
        stackPane.getChildren().add(grid);
    }

    private void displayStudentMenu(StackPane stackPane) {
        VBox studentMenu = new VBox(10);
        studentMenu.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Button borrowBookBtn = new Button("Borrow Book");
        borrowBookBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            borrowBook(stackPane);
        });

        Button returnBookBtn = new Button("Return Book");
        returnBookBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            returnBook(stackPane);
        });

        Button viewBorrowedBooksBtn = new Button("View Borrowed Books");
        viewBorrowedBooksBtn.setOnAction(e -> viewBorrowedBooks(stackPane));

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

        studentMenu.getChildren().addAll(borrowBookBtn, returnBookBtn, viewBorrowedBooksBtn, logoutBtn);
        stackPane.getChildren().add(studentMenu);
    }

    private void borrowBook(StackPane stackPane) {
        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(admin.getBooks()));

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

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn);

        Button borrowBtn = new Button("Borrow");
        borrowBtn.setOnAction(e -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                borrowedBooks.computeIfAbsent(loggedInStudent.getNim(), k -> FXCollections.observableArrayList()).add(selectedBook);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a book to borrow.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, borrowBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private void returnBook(StackPane stackPane) {
        List<Book> books = borrowedBooks.get(loggedInStudent.getNim());
        if (books == null || books.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Books", "You have not borrowed any books.");

            displayStudentMenu(stackPane);
            return;
        }

        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(books));

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

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn);

        Button returnBtn = new Button("Return");
        returnBtn.setOnAction(e -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                books.remove(selectedBook);
                if (books.isEmpty()) {
                    borrowedBooks.remove(loggedInStudent.getNim());
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully.");
                table.setItems(FXCollections.observableArrayList(books));
            } else {
                showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a book to return.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, returnBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private void viewBorrowedBooks(StackPane stackPane) {
        List<Book> books = borrowedBooks.get(loggedInStudent.getNim());
        if (books == null || books.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Books", "You have not borrowed any books.");
            return;
        }

        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(books));

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

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private GridPane createFormPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
