package com.everlapp.androidexamples.workwithdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContentProviderActivity extends AppCompatActivity {

    final Uri CONTACT_URI = Uri.parse("content://com.everlapp.myprovider.AdressBook/contacts");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ContentResolver идет в packageManager и ищет другой процесс через Binder
        // ContentResolver - клиентская часть content provider
        Cursor cursor = getContentResolver().query(
                UserDictionary.Words.CONTENT_URI,
                null,
                null,
                null,
                null
        );


        // Insert data
        ContentValues cvInsert = new ContentValues();
        cvInsert.put(UserDictionary.Words.LOCALE, "en_US");
        cvInsert.put(UserDictionary.Words.WORD, "insert");
        Uri insertUri = getContentResolver().insert(UserDictionary.Words.CONTENT_URI, cvInsert);


        // Update data
        ContentValues cvUpdate = new ContentValues();
        String selectionClause = UserDictionary.Words.LOCALE + "LIKE?";
        String[] selectionArgs = {"en_%"};
        int rowsUpdated = 0;
        cvUpdate.putNull(UserDictionary.Words.LOCALE);

        rowsUpdated = getContentResolver().update(
                UserDictionary.Words.CONTENT_URI,
                cvUpdate,
                selectionClause,
                selectionArgs);


        // Delete data
        String selectionClauseDelete = UserDictionary.Words.APP_ID + "LIKE?";
        String[] selectionArgsDelete = {"user"};
        int rowsDeleted = 0;

        rowsDeleted = getContentResolver().delete(
                UserDictionary.Words.CONTENT_URI,
                selectionClause,
                selectionArgsDelete);

    }


    private void accessToCustomProvider() {
        Cursor cursor = getContentResolver().query(CONTACT_URI,
                null, null, null, null);
        startManagingCursor(cursor);

        // getContentResolver().insert(CONTACT_URI, ...)
        // getContentResolver().update(CONTACT_URI, ...)
        // getContentResolver().delete(CONTACT_URI, ...)

    }



}
