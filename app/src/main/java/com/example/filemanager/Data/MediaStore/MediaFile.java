package com.example.filemanager.Data.MediaStore;

import android.net.Uri;

public abstract class MediaFile
{
    Uri url;

    public Uri getUrl() {
        return url;
    }

    public MediaFile(Uri uri) {
        this.url = uri;
    }

}