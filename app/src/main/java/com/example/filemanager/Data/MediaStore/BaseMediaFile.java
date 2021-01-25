package com.example.filemanager.Data.MediaStore;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseMediaFile
{
    protected Uri uri;

    protected String name;

    protected String MIMEType;

    protected Date date;

    protected int size;

    SimpleDateFormat dateFormatter;


    public BaseMediaFile(Uri url, String name, String MIMEType, Date date, int size) {
        this.uri = url;
        this.name = name;
        this.MIMEType = MIMEType;
        this.date = date;
        this.dateFormatter  = new SimpleDateFormat();
        this.size = size;
    }

    public void setMIMEType(String type){
        this.MIMEType = type;
    }

    public Uri getUri() {
        return uri;
    }

    public BaseMediaFile(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getMIMEType() {
        return MIMEType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public String toString() {



        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("\n\n")

                // /content:/media/external/images/media/9052
                .append("Location: ").append(new File(uri.toString()).getAbsolutePath()).append("\n")
                .append("MIMEtype: ").append(MIMEType).append("\n")
                .append("Date Modified: ").append(dateFormatter.format(date)).append("\n")
                .append("Size: ").append(size)
                .toString();

    }
}