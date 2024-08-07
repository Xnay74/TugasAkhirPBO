package com.example.pbotugasbesar;

import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Book  {
    private final int id;
    private final String title;
    private final String genre;
    private final String author;
    private final int year;
    private int stock;
    private LocalDate borrowDate;
    private int borrowDuration;
    private boolean borrowed;
    private LocalDate returnDate;

    public Book(int id, String title, String genre, String author, int year, int stock) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.year = year;
        this.stock = stock;
        this.borrowed = false;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}

