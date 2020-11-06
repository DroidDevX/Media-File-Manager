package com.example.filemanager.Data.MockData;

import com.example.filemanager.Data.FileItemModel.FileItem;
import com.example.filemanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<FileItem> createRandomFileItems(int listsize){
        ArrayList<FileItem> items = new ArrayList<>();
        int count =1;
        while(count <=listsize){
            items.add(new FileItem("Random file "+count));
            count++;
        }
        return items;
    }
}
