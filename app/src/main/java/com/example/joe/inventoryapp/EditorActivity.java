package com.example.joe.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.inventoryapp.data.BookContract;
import com.example.joe.inventoryapp.data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierPhoneNumberEditText;

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        titleEditText = findViewById(R.id.edit_book_title);
        priceEditText = findViewById(R.id.edit_book_price);
        quantityEditText = findViewById(R.id.edit_book_quantity);
        supplierNameEditText = findViewById(R.id.edit_supplier_name);
        supplierPhoneNumberEditText = findViewById(R.id.edit_supplier_phone_number);
    }

    private void insertBook() {

        String titleString = titleEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();
        String supplierNameString = supplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = supplierPhoneNumberEditText.getText().toString().trim();
        Double price = Double.parseDouble(priceString);
        int quantity = Integer.parseInt(quantityString);

        BookDbHelper bookDbHelper = new BookDbHelper(this);
        SQLiteDatabase database = bookDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_PRODUCT_NAME, titleString);
        values.put(BookContract.BookEntry.COLUMN_PRICE, price);
        values.put(BookContract.BookEntry.COLUMN_QUANTITY, quantity);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);

        long newRowId = database.insert(BookContract.BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBook();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
