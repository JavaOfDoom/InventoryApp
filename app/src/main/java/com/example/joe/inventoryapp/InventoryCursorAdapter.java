package com.example.joe.inventoryapp;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.inventoryapp.data.BookContract.BookEntry;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView titleTextView = view.findViewById(R.id.book_title);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        Button saleButton = view.findViewById(R.id.sale_button);

        int titleColumnIndex = cursor.getColumnIndexOrThrow(BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndexOrThrow(BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndexOrThrow(BookEntry.COLUMN_QUANTITY);
        int uriColumnIndex = cursor.getColumnIndexOrThrow(BookEntry._ID);

        String bookTitle = cursor.getString(titleColumnIndex);
        double bookPrice = cursor.getDouble(priceColumnIndex);
        final int bookQuantity = cursor.getInt(quantityColumnIndex);
        final Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, cursor.getInt(uriColumnIndex));

        titleTextView.setText(bookTitle);
        priceTextView.setText(Double.toString(bookPrice));
        quantityTextView.setText(Integer.toString(bookQuantity));

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookQuantity > 0) {
                    int newQuantity = bookQuantity - 1;
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_QUANTITY, newQuantity);
                    context.getContentResolver().update(uri, values, null, null);
                } else {
                    Toast.makeText(context, R.string.out_of_stock, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
