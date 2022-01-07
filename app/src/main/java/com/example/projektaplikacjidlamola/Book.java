package com.example.projektaplikacjidlamola;

import java.io.Serializable;

public class Book implements Serializable {
    public Book(String authors, String title, String description) {
        this.authors = authors;
        this.title = title;
        this.description = description;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return title;
    }

    String authors;
    String title;
    String description;
}
