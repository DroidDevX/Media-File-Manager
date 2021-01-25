package com.example.filemanager.Data.MediaStore.AudioStore;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.filemanager.Data.MediaStore.BaseMediaStore;
import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Accesses the Audio folder in shared local storage by using MediaStore API
 * */
public class AudioStore extends BaseMediaStore {
    private static final String TAG = "AudioStore";
    public AudioStore(Context context) {
        super(context);
    }

    @Override
    public List<BaseMediaFile> getFiles() {
        List<BaseMediaFile> audiolist = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media.SIZE
        };
        String selection = null;
        String[] selectionArgs = new String[]{};
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int size = cursor.getInt(sizeColumn);
                Date date = new  Date((int)cursor.getDouble(dateColumn));

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String fileMIMEtype = getContext().getContentResolver().getType(contentUri);
                audiolist.add(new AudioFileBase(contentUri, name, size,fileMIMEtype, date));
            }
        } catch (Exception e) {
            Log.e(TAG,"Error:"+e.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return audiolist;

    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
