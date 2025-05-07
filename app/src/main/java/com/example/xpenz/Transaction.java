package com.example.xpenz;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private String title;
    private String type;
    private double amount;
    private String note;
    private String date;

    public Transaction(int id, String title, String type, double amount, String note, String date) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }
}