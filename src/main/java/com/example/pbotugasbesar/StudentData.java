package com.example.pbotugasbesar;

public class StudentData  {

    private String name;
    private String nim;
    private String faculty;
    private String department;

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
