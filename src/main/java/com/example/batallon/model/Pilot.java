package com.example.batallon.model;

public class Pilot {
    private int id;
    private String name;
    private String rank;
    private int age;

    public Pilot() {}

    public Pilot(int id, String name, String rank, int age) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
