package com.example.filemanager.Data.MediaStore.ImageStore;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.filemanager.Data.MediaStore.BaseMediaStore;
import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Accesses the Image folder in shared local storage by using MediaStore API
 * */
public class ImageStore extends BaseMediaStore {
    private static final String TAG = "ImageStore";

    public ImageStore(Context context) {
        super(context);
    }

    @Override
    public List<BaseMediaFile> getFiles() {
        Log.d(TAG, "getFiles: ");
        List<BaseMediaFile> imageList = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE
        };
        String selection = null;
        String[] selectionArgs = new String[]{};
        String sortOrder = MediaStore.Images.Media.TITLE + " ASC";
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );


            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int size = cursor.getInt(sizeColumn);
                Date dateModifed = new Date( (long)(cursor.getDouble(dateColumn)*1000)); //unix timestamp is in seconds. We need milisconds
                Log.d(TAG, "getFiles: Date raw int ->"+ (int)cursor.getDouble(dateColumn)*1000);
                Log.d(TAG, "getFiles: , Date modified raw string -> "+dateModifed.toString());

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                String MIMEType = getContext().getContentResolver().getType(contentUri);
                imageList.add(new ImageFileBase(contentUri, name, size,MIMEType,dateModifed));
                Log.d(TAG, "getFiles - new Image file added, uri ="+contentUri.toString());
            }
        } catch (Exception e) {
            Log.e(TAG,"Error: "+e.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return imageList;

    }
}
