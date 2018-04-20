package com.everlapp.androidexamples.workwithdata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProviderExample extends ContentProvider{




    @Override
    public boolean onCreate() {
        // Необходимо минимизировать длительные операции
        // Здесь можно вызвать конструктор БД

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        return null;
    }

    // Return MIME type
    // for 1 string     - vnd.android.cursor.item/
    // for directory    - vnd.android.cursor.dir/
    // обычные MIME type
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    // возвращает URI объекта, котрый был вставлен
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
        return null;
    }

    // возвращает количество удаленных записей
    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    // возвращает количество записей, которые были обновлены
    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }


}
