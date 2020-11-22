package com.example.androidprojectbooksales.books;

import java.util.ArrayList;

public class BookCollection {

    BookCollection instance = null;
    ArrayList<Book> list;

    public BookCollection getInstance() {
        if (instance == null) {
            instance = new BookCollection();
        }
        return instance;
    }

    public ArrayList<Book> getList() {
        return list;
    }

    public void addBook(Book book) {
        list.add(book);
    }
}
