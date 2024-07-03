package com.example.pbotugasbesar;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.print.PrinterJob;


import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Student  {

    private StudentData loggedInStudent;
    private final Map<String, List<Book>> borrowedBooks = new HashMap<>();
    private final Admin admin;
    private CharSequence borrowHistory;


    public Student(Admin admin) {
        this.admin = admin;
        admin.getStudents().add(new StudentData("Gery","202310370311101","Teknik","Informatika"));
        admin.getStudents().add(new StudentData("Joni", "202010360311111", "HUKUM", "HUKUM"));
        admin.getStudents().add(new StudentData("Budi","202310380311100","FIKES","Kedokteran"));

        admin.getBooks().add(new Book(3, "Perang COC","History","SuperGuru",2020,3));
        admin.getBooks().add(new Book(4, "GajahTari","Story","Bubu",2015,3));
        admin.getBooks().add(new Book(5, "7 Pahlawan", "Fantasy", "Mekdi", 2019, 5));

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
            for (StudentData student : admin.getStudents()) {
                if (student.getNim().equals(nimField.getText())) {
                    loggedInStudent = student;
                    stackPane.getChildren().clear();
                    displayStudentMenu(stackPane);
                    return;
                }
            }
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Student not found.");
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

        Button historyBtn = new Button("View History Borrowed Books");
        historyBtn.setOnAction(e -> viewHistoryBorrowedBook(stackPane) );

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



        studentMenu.getChildren().addAll(borrowBookBtn, returnBookBtn, viewBorrowedBooksBtn,historyBtn, logoutBtn);
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

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn, stockColumn);

        TextField daysField = new TextField();
        daysField.setPromptText("Enter number of days (max 7)");

        Button borrowBtn = new Button("Borrow");
        borrowBtn.setOnAction(e -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                try {
                    int days = Integer.parseInt(daysField.getText());
                    if (days > 0 && days <= 7) {
                        selectedBook.setStock(selectedBook.getStock() - 1);
                        selectedBook.setBorrowDate(LocalDate.now());
                        selectedBook.setBorrowDuration(days);
                        selectedBook.setBorrowed(true);

                        borrowedBooks.computeIfAbsent(loggedInStudent.getNim(), k -> new ArrayList<>()).add(selectedBook);

                        showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully.");
                        table.refresh();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number of days (1-7).");
                    }
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number of days.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a book to borrow.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, daysField, borrowBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }
    private void viewBorrowedBooks(StackPane stackPane) {
        List<Book> studentBooks = borrowedBooks.getOrDefault(loggedInStudent.getNim(), new ArrayList<>());
        if (studentBooks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Books", "You have not borrowed any books.");
            return;
        }

        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(studentBooks));

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

        TableColumn<Book, LocalDate> borrowDateColumn = new TableColumn<>("Borrow Date");
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        TableColumn<Book, Integer> borrowDurationColumn = new TableColumn<>("Borrow Duration (days)");
        borrowDurationColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDuration"));

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn, borrowDateColumn, borrowDurationColumn);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
        });
        Button printBtn = new Button("Print");
        printBtn.setOnAction(e -> {
            // Dapatkan tabel sebagai Node
            Node tableNode = table;

            // Buat objek PrinterJob
            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null && job.showPrintDialog(tableNode.getScene().getWindow())) {
                boolean success = job.printPage(tableNode);
                if (success) {
                    job.endJob();
                }
            }
        });

        VBox vbox = new VBox(10, table, backBtn, printBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
}

    private void returnBook(StackPane stackPane) {
        List<Book> studentBooks = borrowedBooks.getOrDefault(loggedInStudent.getNim(), new ArrayList<>());
        if (studentBooks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Books", "You have no borrowed books to return.");
            stackPane.getChildren().clear();
            displayStudentMenu(stackPane);
            return;
        }

        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(studentBooks));

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

        TableColumn<Book, LocalDate> returnDateColumn = new TableColumn<>("Return Date");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn, stockColumn, returnDateColumn);

        Button returnBtn = new Button("Return");
        returnBtn.setOnAction(e -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                selectedBook.setStock(selectedBook.getStock() + 1);
                selectedBook.setReturnDate(LocalDate.now());
                studentBooks.remove(selectedBook);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully.");
                table.refresh();
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


    private void viewHistoryBorrowedBook(StackPane stackPane){
        List<Book> studentBooks = new ArrayList<>();
        for (Book book : admin.getBooks()) {
            if (book.isBorrowed()) {
                studentBooks.add(book);
            }
        }

        TableView<Book> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(studentBooks));

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

        TableColumn<Book, LocalDate> returnDateColumn = new TableColumn<>("Return Date");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate")); // Display return date

        table.getColumns().addAll(idColumn, titleColumn, genreColumn, authorColumn, yearColumn, returnDateColumn);

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
        grid.setPadding(new Insets(20));
        return grid;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public StudentData getLoggedInStudent() {
        return loggedInStudent;
    }

    public void setLoggedInStudent(StudentData loggedInStudent) {
        this.loggedInStudent = loggedInStudent;
    }

}
