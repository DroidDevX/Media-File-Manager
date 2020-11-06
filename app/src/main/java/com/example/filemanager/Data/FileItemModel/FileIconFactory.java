package com.example.filemanager.Data.FileItemModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.filemanager.R;
import java.io.File;

public class FileIconFactory {

    public static class FILETYPE {
        public final static String AUDIO="audio";
        public final static String VIDEO="video";
        public final static String IMAGE="image";
        public final static String FOLDER="folder";
        public final static String PDF="pdf";
    }
    public Bitmap getIcon(Context c, File f)
     {
       String FILETYPE = getFileType(c,f);
       Bitmap icon =null;
       switch(FILETYPE)
       {
           case FileIconFactory.FILETYPE.AUDIO:
               icon =BitmapFactory.decodeResource(c.getResources(), R.drawable.icon_audio);
               break;

       }
       return icon;
     }

     private String getFileType(Context c , File F)
     {
       //find MIMEType using Context c and f
         return "";
     }
}
