package com.example.pbotugasbesar;

import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Book  {
    private int id;
    private String title;
    private String genre;
    private String author;
    private int year;
    private int stock;
    private LocalDate borrowDate;
    private int borrowDuration;

    public Book(int id, String title, String genre, String author, int year, int stock) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.year = year;
        this.stock = stock;
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
}

