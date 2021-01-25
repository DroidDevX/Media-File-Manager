package com.example.filemanager.Data.MediaStore.VideoStore;

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
 * Accesses the Video folder in shared local storage by using MediaStore API
 * */
public class VideoStore extends BaseMediaStore {
    private static final String TAG = "VideoStore";
    public VideoStore(Context context) {
        super(context);
    }

    @Override
    public List<BaseMediaFile> getFiles() {

        List<BaseMediaFile> videoList = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                //  MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        };

        String selection = null;//MediaStore.Video.Media.DURATION + " >= ?";
        String[] selectionArgs ={};
        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int size = cursor.getInt(sizeColumn);
                Date dateModified = new Date((int) cursor.getDouble(dateColumn));

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String MIMEtype = getContext().getContentResolver().getType(contentUri);

                videoList.add(new VideoFileBase(contentUri, name, size,MIMEtype,dateModified));
                Log.d(TAG,"Video file found, url = "+contentUri.toString() + ", name = "+name);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :"+e.toString() );
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return videoList;
    }
}

