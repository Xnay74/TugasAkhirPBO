package com.example.pbotugasbesar.data;

import com.example.pbotugasbesar.Main;
import com.example.pbotugasbesar.StudentData;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.example.pbotugasbesar.koneksi.koneksi;

public class Admin  {

    private List<StudentData> students = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private VBox adminMenu;
    private int bookIdCounter = 1;
    private Connection con;


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



        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 2);
        TextField authorField = new TextField();
        GridPane.setConstraints(authorField, 1, 2);

        Label publisherLabel = new Label("Publisher:");
        GridPane.setConstraints(publisherLabel, 0, 2);
        TextField publisherField = new TextField();
        GridPane.setConstraints(publisherField, 1, 2);

        Label yearLabel = new Label("Year:");
        GridPane.setConstraints(yearLabel, 0, 3);
        TextField yearField = new TextField();
        GridPane.setConstraints(yearField, 1, 3);

        Label genreLabel = new Label("Genre:");
        GridPane.setConstraints(genreLabel, 0, 1);
        TextField genreField = new TextField();
        GridPane.setConstraints(genreField, 1, 0);

        Label quantityLabel = new Label("Year:");
        GridPane.setConstraints(quantityLabel, 0, 3);
        TextField quantityField = new TextField();
        GridPane.setConstraints(quantityField, 1, 3);

        Button addBtn = new Button("Add");
        GridPane.setConstraints(addBtn, 1, 4);
        addBtn.setOnAction(e -> {
            String judul = titleField.getText();
            String pengarang = authorField.getText();
            String penerbit = publisherField.getText();
            int tahun_terbit = Integer.parseInt(yearField.getText());
            String kategori = genreField.getText();
            int jumlah = Integer.parseInt(quantityField.getText());
            try {
                Admin.addBook(judul, pengarang, penerbit,tahun_terbit,kategori, jumlah);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book added successfully!");
                alert.show();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add book.");
                alert.show();
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 0, 4);
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        grid.getChildren().addAll(titleLabel, titleField, genreLabel, genreField, authorLabel, authorField, yearLabel, yearField, addBtn, backBtn);
        stackPane.getChildren().add(grid);
    }

    private void displayStudents(StackPane stackPane) {
        TableView<StudentData> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(students));

        TableColumn<StudentData, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<StudentData, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<StudentData, String> facultyColumn = new TableColumn<>("Faculty");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        TableColumn<StudentData, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        table.getColumns().addAll(nameColumn, nimColumn, facultyColumn, departmentColumn);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            stackPane.getChildren().clear();
            displayAdminMenu(stackPane);
        });

        VBox vbox = new VBox(10, table, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        stackPane.getChildren().clear();
        stackPane.getChildren().add(vbox);
    }

    private void displayBooks(StackPane stackPane) {
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
            displayAdminMenu(stackPane);
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


    public static ResultSet getBooksdatabase() throws SQLException {
        String sql = "SELECT * FROM tb_buku";
        Connection conn = koneksi.getkoneksi();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
    public static void addBook(String judul, String pengarang, String penerbit, int tahun_terbit,String kategori, int jumlah) throws SQLException {
        String sql = "INSERT INTO tb_buku (judul, pengarang, penerbit, tahun_terbit,kategori, jumlah) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, judul);
            stmt.setString(2, pengarang);
            stmt.setString(3, penerbit);
            stmt.setInt(4, tahun_terbit);
            stmt.setString(5, kategori);
            stmt.setInt(6, jumlah);
            stmt.executeUpdate();
        }
    }
    public static void updateBook(int id_buku, String judul, String pengarang, String penerbit, int tahun_terbit,String kategori, int jumlah) throws SQLException {
        String sql = "UPDATE tb_buku SET judul = ?, pengarang = ?, penerbit = ?, tahun_terbit = ?,kategori = ?, jumlah = ? WHERE id_buku = ?";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, judul);
            stmt.setString(2, pengarang);
            stmt.setString(3, penerbit);
            stmt.setInt(4, tahun_terbit);
            stmt.setString(5, kategori);
            stmt.setInt(6, jumlah);
            stmt.setInt(7, id_buku);
            stmt.executeUpdate();
        }
    }
    public static void deleteBook(int id_buku) throws SQLException {
        String sql = "DELETE FROM tb_buku WHERE id_buku = ?";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_buku);
            stmt.executeUpdate();
        }
    }
    public static ResultSet getStudentsdatabase() throws SQLException {
        String sql = "SELECT * FROM tb_mahasiswa";
        Connection conn = koneksi.getkoneksi();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public static void addStudent(String nama, String nim, String fakultas, String jurusan, String telepon, String email) throws SQLException {
        String sql = "INSERT INTO tb_mahasiswa (nama, nim, fakultas, jurusan, telepon, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama);
            stmt.setString(2, nim);
            stmt.setString(3, fakultas);
            stmt.setString(4, jurusan);
            stmt.setString(5, telepon);
            stmt.setString(6, email);
            stmt.executeUpdate();
        }
    }
    public static void updateStudent(int id_mahasiswa, String nama, String nim, String fakultas, String jurusan, String telepon, String email) throws SQLException {
        String sql = "UPDATE tb_buku SET nama = ?, nim = ?, fakultas = ?, jurusan = ?, telepon = ?, email = ? WHERE id_mahasiswa = ?";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama);
            stmt.setString(2, nim);
            stmt.setString(3, fakultas);
            stmt.setString(4, jurusan);
            stmt.setString(5, telepon);
            stmt.setString(6, email);
            stmt.setInt(7, id_mahasiswa);
            stmt.executeUpdate();
        }
    }
    public static void deleteStudent(int id_mahasiswa) throws SQLException {
        String sql = "DELETE FROM tb_mahasiswa WHERE id_mahasiswa = ?";
        try (Connection conn = koneksi.getkoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_mahasiswa);
            stmt.executeUpdate();
        }
    }

}
