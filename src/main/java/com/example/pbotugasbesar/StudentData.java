package com.example.pbotugasbesar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentData {

    private final StringProperty nama;
    private final StringProperty nim;
    private final StringProperty fakultas;
    private final StringProperty jurusan;
    private final StringProperty telepon;
    private final StringProperty email;

    public StudentData(String nama, String nim, String fakultas, String jurusan, String telepon, String email) {
        this.nama = new SimpleStringProperty(nama);
        this.nim = new SimpleStringProperty(nim);
        this.fakultas = new SimpleStringProperty(fakultas);
        this.jurusan = new SimpleStringProperty(jurusan);
        this.telepon = new SimpleStringProperty(telepon);
        this.email = new SimpleStringProperty(email);
    }

    public StringProperty nameProperty() {
        return nama;
    }

    public StringProperty nimProperty() {
        return nim;
    }

    public StringProperty fakultasProperty() {
        return fakultas;
    }

    public StringProperty jurusanProperty() {
        return jurusan;
    }

    public StringProperty teleponProperty() {
        return telepon;
    }

    public StringProperty emailProperty() {
        return email;
    }
}
