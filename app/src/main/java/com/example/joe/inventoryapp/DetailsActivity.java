package com.example.joe.inventoryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.inventoryapp.data.BookContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_BOOK_LOADER = 0;
    @BindView(R.id.details_book_title)
    TextView titleDisplayText;
    @BindView(R.id.details_price)
    TextView priceDisplayText;
    @BindView(R.id.details_quantity_number)
    TextView quantityNumberDisplayText;
    @BindView(R.id.details_supplier_name)
    TextView supplierNameDisplayView;
    @BindView(R.id.details_supplier_phone_number)
    TextView supplierPhoneNumberDisplayText;
    @BindView(R.id.details_decrease)
    Button decreaseBook;
    @BindView(R.id.details_increase)
    Button increaseBook;
    @BindView(R.id.details_order)
    Button orderBook;
    @BindView(R.id.details_edit)
    Button editBook;
    @BindView(R.id.details_delete)
    Button deleteBook;
    private int quantity;
    private String supplierPhoneNumber;
    private Uri currentBookUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        Intent intent = getIntent();
        currentBookUri = intent.getData();

        setTitle(getString(R.string.details_title));
        getLoaderManager().initLoader(EXISTING_BOOK_LOADER, null, this);

        ButterKnife.bind(this);

        decreaseBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_QUANTITY, quantity);
                    DetailsActivity.this.getContentResolver().update(currentBookUri,
                            values,
                            null,
                            null);
                } else {
                    Toast.makeText(DetailsActivity.this, R.string.out_of_stock,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        increaseBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_QUANTITY, quantity);
                DetailsActivity.this.getContentResolver().update(currentBookUri,
                        values,
                        null,
                        null);
            }
        });

        orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrder = new Intent(Intent.ACTION_DIAL);
                intentOrder.setData(Uri.parse("tel:" + supplierPhoneNumber.trim()));

                if (intentOrder.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentOrder);
                }
            }
        });

        editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(DetailsActivity.this, EditorActivity.class);
                intentEdit.setData(currentBookUri);
                startActivity(intentEdit);
            }
        });

        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER};
        return new CursorLoader(this,
                currentBookUri,
                projection,
                null,
                null,
                null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            String title = cursor.getString(titleColumnIndex);
            double price = cursor.getDouble(priceColumnIndex);
            quantity = cursor.getInt(quantityColumnIndex);
            String supplierName = cursor.getString(supplierNameColumnIndex);
            supplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

            titleDisplayText.setText(title);
            priceDisplayText.setText("$ " + Double.toString(price));
            quantityNumberDisplayText.setText(Integer.toString(quantity));
            supplierNameDisplayView.setText(supplierName);
            supplierPhoneNumberDisplayText.setText(supplierPhoneNumber);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        titleDisplayText.setText("");
        priceDisplayText.setText("");
        quantityNumberDisplayText.setText("");
        supplierNameDisplayView.setText("");
        supplierPhoneNumberDisplayText.setText("");
    }

    private void deleteBook() {
        if (currentBookUri != null) {
            int rowsDeleted = getContentResolver().delete(currentBookUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
