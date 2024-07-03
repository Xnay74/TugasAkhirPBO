package com.example.pbotugasbesar;

public class StudentData  {

    private final String name;
    private final String nim;
    private final String faculty;
    private final String department;

    public StudentData(String name, String nim, String faculty, String department) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }
}
