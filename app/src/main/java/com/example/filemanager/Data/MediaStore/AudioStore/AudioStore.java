package com.example.filemanager.Data.MediaStore.AudioStore;

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
 * Accesses the Audio folder in shared local storage by using MediaStore API
 * */
public class AudioStore extends BaseMediaStore {

    public AudioStore(Context context) {
        super(context);
    }

    @Override
    public List<MediaFile> getFiles() {
        List<MediaFile> audiolist = new ArrayList<>();

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
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED);
            //int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                //int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                audiolist.add(new AudioFile(contentUri, name, size));
            }
        } catch (Exception e) {
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
