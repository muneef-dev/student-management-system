package com.developerstack.edumanage.model;

public class Program {
    private String code;
    private String name;
    private String[] technologies; // izukku arrayList poattendha leasi 777777
    private String teacherId; // program ikku data add panna mundhi teacher a add seyyanum
    private double cost;

    public Program() {
    }

    public Program(String code, String name, String[] technologies, String teacherId, double cost) {
        this.code = code;
        this.name = name;
        this.technologies = technologies;
        this.teacherId = teacherId;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
