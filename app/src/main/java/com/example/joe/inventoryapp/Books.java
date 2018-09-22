package com.example.joe.inventoryapp;

public class Books {

    private String bookTitle;

    private String bookPrice;

    private String bookQuantity;

    private String bookSupplierName;

    private String bookSupplierPhoneNumber;

    public Books(String title, String price, String quantity, String supplierName, String supplierPhoneNumber) {
        bookTitle = title;
        bookPrice = price;
        bookQuantity = quantity;
        bookSupplierName = supplierName;
        bookSupplierPhoneNumber = supplierPhoneNumber;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public String getBookSupplierName() {
        return bookSupplierName;
    }

    public String getBookSupplierPhoneNumber() {
        return bookSupplierPhoneNumber;
    }
}
