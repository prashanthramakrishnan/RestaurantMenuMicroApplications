package com.prashanth.restaurantmenudata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class RestaurantMenuResponseContentProvider extends ContentProvider {

    private static final String[] contentProviderColumns = {"requestURI", "response"};

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(contentProviderColumns);
        //this is the uri of the get request
        String key = "http://someremoteurl.com/sample.json";
        String response = null;
        try {
            response = IOUtils.toString(RestaurantMenuDataApplication.getAppContext().getAssets().open("json/menu_response.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        cursor.addRow(new Object[]{key, response.getBytes()});
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
