package com.example.filemanager.Data.MediaStore.ImageStore;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.filemanager.Data.MediaStore.BaseMediaStore;
import com.example.filemanager.Data.MediaStore.MediaFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Accesses the Image folder in shared local storage by using MediaStore API
 * */
public class ImageStore extends BaseMediaStore {


    public ImageStore(Context context) {
        super(context);
    }

    @Override
    public List<MediaFile> getFiles() {

        List<MediaFile> imageList = new ArrayList<>();

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
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
            //int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                //int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                imageList.add(new ImageFile(contentUri, name, size));
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return imageList;

    }
}
