package com.example.filemanager.Util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

/**
 * Helper class: Launches app chooser given the File uri
 * */
public class AppChooserUtil {
    private static final String TAG = "AppChooserUtil";

    public static void openFile(Context context, Uri uri){
        Log.e(TAG, "openFile: uri path ->"+uri.getPath());
        //Get MIMEType
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = "application/"+mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        Log.e(TAG,"MIME TYPE ->:"+type);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setData(uri);

        //Grant URI permissions to apps opening the file
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY| Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}
