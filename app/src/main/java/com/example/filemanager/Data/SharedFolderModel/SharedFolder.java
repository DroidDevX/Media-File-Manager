package com.example.filemanager.Data.SharedFolderModel;

public class SharedFolder {

    String name;
    int iconID;

    public SharedFolder(String name, int iconID) {
        this.name = name;
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
