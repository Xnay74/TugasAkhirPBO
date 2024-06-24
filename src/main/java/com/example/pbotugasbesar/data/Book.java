package com.example.pbotugasbesar.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Book  {

    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty publisher;
    private final IntegerProperty year;
    private final IntegerProperty quantity;

    public Book(String title, String author, String publisher, int year, int quantity) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.year = new SimpleIntegerProperty(year);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

}
