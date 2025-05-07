package com.example.xpenz;

public class Transaction {
    private String title;
    private double amount;
    private String type;
    private String tags;
    private String note;
    private String date;
    private String time;

    public Transaction(String title, double amount, String type, String tags, String note, String date, String time) {
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.tags = tags;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getTags() { return tags; }
    public String getNote() { return note; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}