package com.example.pbotugasbesar;

import java.time.LocalDate;

public class viewBorrowedBook {
    private int id;
    private String title;
    private String genre;
    private String author;
    private int year;
    private int stock;
    private LocalDate borrowDate;
    private int borrowDuration;
    private String studentName; // Add student name
    private String studentNim;

    public viewBorrowedBook(int id, String title, String genre, String author, int year, int stock, String studentName, String studentNim) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.year = year;
        this.stock = stock;
        this.studentName = studentName;
        this.studentNim = studentNim;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public  String getStudentName(){return studentName;}

    public  String getStudentNim(){return studentNim;}

    public int getYear() {
        return year;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getBorrowDuration() {
        return borrowDuration;
    }

    public void setBorrowDuration(int borrowDuration) {
        this.borrowDuration = borrowDuration;
    }
}
