package com.example.filemanager.Data.FileItemModel;

import java.io.File;

public class FileItem {

    String filename;
    int iconResourceID;

    public FileItem(String filename, int iconResourceID) {
        this.filename =filename;
        this.iconResourceID = iconResourceID;
    }

    public FileItem(String filename){
        this.filename = filename;
        this.iconResourceID =-1;
    }

    public String getFileName() {
        return filename;
    }

    public int getIconResourceID(){
        return iconResourceID;
    }

}
