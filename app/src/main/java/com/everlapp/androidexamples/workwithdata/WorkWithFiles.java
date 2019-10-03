package com.everlapp.androidexamples.workwithdata;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WorkWithFiles {


    /**
     * Asset folder app/src/main/assets
     *
     * @param context
     * @param filename
     * @return
     */
    @Nullable
    public Bitmap loadBitmapFromAssets(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("images/" + filename);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Nullable
    public Bitmap loadBitmapFromCacheDirInternal(Context context, String filename) {
        File cacheDir = context.getCacheDir();
                        // context.getFilesDir();
                        // context.getFileStreamPath(filename);

        File file = new File(cacheDir, filename);
        if (file.exists()) { return getBitmapFromFile(file); }
        return null;
    }


    @Nullable
    public Bitmap loadBitmapFromCacheDirExternal(Context context, String filename) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = Environment.getExternalStorageDirectory();
                            // context.getExternalCacheDir();
                            // context.getExternalFilesDir("type");

            File file = new File(cacheDir, filename);                      // Root DIR !!!
            if (file.exists()) { return getBitmapFromFile(file); }
        }
        return null;
    }


    @Nullable
    private Bitmap getBitmapFromFile(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try { is.close();
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }



}
