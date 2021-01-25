package com.example.filemanager.Data.MediaStore.AudioStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.util.Date;

public class AudioFileBase extends BaseMediaFile {




    public AudioFileBase(Uri url, String name, int size, String MIMEtype, Date dateModifed) {
        super(url, name,MIMEtype, dateModifed,size);

    }




}
