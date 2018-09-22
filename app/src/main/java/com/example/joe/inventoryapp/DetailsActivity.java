package com.example.joe.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.joe.inventoryapp.data.BookContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISITNG_BOOK_LOADER = 0;

    private Uri currentBookUri;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        Intent intent = getIntent();
        currentBookUri = intent.getData();

        setTitle("Details");
        getLoaderManager().initLoader(EXISITNG_BOOK_LOADER, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);

        ButterKnife.bind(this);
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
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

            titleDisplayText.setText(title);
            priceDisplayText.setText(Double.toString(price));
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

}
