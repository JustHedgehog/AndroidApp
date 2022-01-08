package com.example.projektaplikacjidlamola;

import java.io.Serializable;

public class Book implements Serializable {
    public Book(String authors, String title, String description) {
        this.authors = authors;
        this.title = title;
        this.description = description;
    }

    public Book(String authors, String title, String description, Boolean read, Boolean toRead , Boolean like){
        this.authors = authors;
        this.title = title;
        this.description = description;
        this.read = read;
        this.toRead = toRead;
        this.like = like;
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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getToRead() {
        return toRead;
    }

    public void setToRead(Boolean toRead) {
        this.toRead = toRead;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    String authors;
    String title;
    String description;
    Boolean read = false;
    Boolean toRead = false;
    Boolean like = false;

}
