package com.example.pbotugasbesar;

import javafx.application.Application;
import javafx.stage.Stage;

public class Book  {

    private int id;
    private String title;
    private String genre;
    private String author;
    private int year;

    public Book(int id, String title, String genre, String author, int year) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.year = year;
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

}
