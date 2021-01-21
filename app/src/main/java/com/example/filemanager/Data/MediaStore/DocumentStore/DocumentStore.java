package com.example.filemanager.Data.MediaStore.DocumentStore;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.filemanager.Data.MediaStore.BaseMediaStore;
import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Accesses the Documents folder in shared local storage by using MediaStore API
 * */
public class DocumentStore  extends BaseMediaStore {
    private static final String TAG = "DocumentStore";
    public DocumentStore(Context context) {
        super(context);
    }


    /*
    * New solution:
    * Click button-> pops up alert dialog -> tell user to select file from system picker
    *  -> url of selected file is returned in onActivityResult -> log the url of the chosen file
    *  -> start the app chooser given the url of the selected file
    *
    *
    *
    * */

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public List<BaseMediaFile> getFiles() {
        Log.d(TAG, "getFiles: ");
        List<BaseMediaFile> documentList = new ArrayList<>();

        Uri table=MediaStore.Files.getContentUri("external");
        String where = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?";

        String[] args ={
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtf"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("html")
        };

        String[] columns = new String[]{
                MediaStore.Files.FileColumns.DATA
        };
        //exclude media files
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(
                    table,
                    columns,
                    where,
                    args,
                    sortOrder
            );

            // Cache column indices.
            int data = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

            while (cursor.moveToNext()) {

            }
        } catch (Exception e) {
            Log.e(TAG, "Error: "+e.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return documentList;
    }
}
