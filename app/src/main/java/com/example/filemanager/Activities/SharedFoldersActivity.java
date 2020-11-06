package com.example.filemanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filemanager.Adapters.FileItemAdapter;
import com.example.filemanager.CustomViews.CustomToolbar;
import com.example.filemanager.Data.FileItemModel.FileItem;
import com.example.filemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays media folders that are publicly accessible to all apps on the Android device.
 * Namely : Audio, Image, Video and Documents
 * */
public class SharedFoldersActivity extends AppCompatActivity {

    FileItemAdapter fileAdapter;
    RecyclerView fileRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_folders);
        CustomToolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setText("Shared Folders");
        Glide.with(this).load(R.drawable.icon_folder).into(toolbar.iconView());

        setupRecyclerView();
    }

    public void setupRecyclerView(){
        fileAdapter = new FileItemAdapter(createDefaultFolders());
        final Context activityContext = this;
        fileAdapter.addItemEventListener(new FileItemAdapter.ItemEventListener() {
            @Override
            public void onLongClick(FileItem item, int itemPos) {
                //empty hook
            }

            @Override
            public void onClick(FileItem item, int itemPos) {
                String targetfolder=null;
                switch (item.getFileName()){
                    case "Audio":
                        targetfolder = FolderDetailActivity.AUDIO_FOLDER;
                        break;
                    case "Video":
                        targetfolder = FolderDetailActivity.VIDEO_FOLDER;
                        break;
                    case "Image":
                        targetfolder = FolderDetailActivity.IMAGE_FOLDER;
                        break;
                    case "Document":
                        targetfolder = FolderDetailActivity.DOCUMENT_FOLDER;
                    default:break;
                }
                Intent i = new Intent(activityContext,FolderDetailActivity.class);
                i.putExtra(FolderDetailActivity.INTENT_EXTRA_TARGET_FOLDER,targetfolder);
                i.putExtra(FolderDetailActivity.INTENT_EXTRA_APPBAR_ICON,item.getIconResourceID());
                startActivity(i);
            }
        });

        fileRecyclerView = findViewById(R.id.fileRecyclerview);
        fileRecyclerView.setAdapter(fileAdapter);
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<FileItem> createDefaultFolders(){
        List<FileItem> folders = new ArrayList<>();
        folders.add(new FileItem("Audio", R.drawable.icon_audio));
        folders.add(new FileItem("Video", R.drawable.icon_video));
        folders.add(new FileItem("Image", R.drawable.icon_image));
        folders.add(new FileItem("Document", R.drawable.icon_document));
        return folders;
    }


}
