package com.example.bhumihar.mafiv;

/**
 * Created by Diksha on 27-10-2016.
 */
public class book {
    private int id;
    private String book_name;
    private String author;
    private String type;
    private String availability;
    private String issue_by;
    private String return_by;

    //constructor


    public book(int id, String book_name, String author, String type, String availability, String issue_by, String return_by) {
        this.id = id;
        this.book_name = book_name;
        this.author = author;
        this.type = type;
        this.availability = availability;
        this.issue_by = issue_by;
        this.return_by = return_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getIssue_by() {
        return issue_by;
    }

    public void setIssue_by(String issue_by) {
        this.issue_by = issue_by;
    }

    public String getReturn_by() {
        return return_by;
    }

    public void setReturn_by(String return_by) {
        this.return_by = return_by;
    }
}
