package com.example.joe.inventoryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Books> {

    public BookAdapter(Activity context, ArrayList<Books> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_details, parent, false);
        }

        Books currentBook = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.details_book_title);

        titleTextView.setText(currentBook.getBookTitle());

        TextView priceTextView = convertView.findViewById(R.id.details_price);

        priceTextView.setText(currentBook.getBookPrice());

        TextView quantityTextView = convertView.findViewById(R.id.details_quantity_number);

        quantityTextView.setText(currentBook.getBookQuantity());

        TextView supplierNameTextView = convertView.findViewById(R.id.details_supplier_name);

        supplierNameTextView.setText(currentBook.getBookSupplierName());

        TextView supplierPhoneNumberTextView = convertView.findViewById(R.id.details_supplier_phone_number);

        supplierPhoneNumberTextView.setText(currentBook.getBookSupplierPhoneNumber());

        return convertView;
    }
}
