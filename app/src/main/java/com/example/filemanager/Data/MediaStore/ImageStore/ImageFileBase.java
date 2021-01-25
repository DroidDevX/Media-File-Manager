package com.example.filemanager.Data.MediaStore.ImageStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.util.Date;

public class ImageFileBase extends BaseMediaFile {




    @Override
    public Uri getUri() {
        return super.getUri();
    }



    public ImageFileBase(Uri url, String name, int size, String MIMEtype, Date dateModified) {
        super(url, name,MIMEtype, dateModified,size);

    }

}
