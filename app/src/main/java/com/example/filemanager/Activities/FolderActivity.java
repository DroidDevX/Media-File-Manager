package com.example.filemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.filemanager.Adapters.InternalStorageAdapter;
import com.example.filemanager.Data.InternalStorageRepository.InternalStorageRepository;
import com.example.filemanager.R;
import com.example.filemanager.ViewModel.InternalStorageViewModel;

import java.io.File;
import java.util.List;

public class FolderActivity extends AppCompatActivity {
    private static final String TAG = "InternalStorageFolderDe";
    final public static String ACTION_DISPLAY_DIRECTORY_CONTENTS ="dispDirContents";

    InternalStorageViewModel viewModel;
    InternalStorageAdapter fileAdapter;
    RecyclerView fileRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage_folder_detail);

        String directoryPath = getIntent().getStringExtra(ACTION_DISPLAY_DIRECTORY_CONTENTS);
        if(directoryPath!=null){
            setupViewmodel(directoryPath);
            setupRecyclerview();
        }
    }

    public void setupViewmodel(String parentDirectoryPath){
        viewModel = new InternalStorageViewModel(InternalStorageRepository.getInstance(),parentDirectoryPath);
        fileAdapter = new InternalStorageAdapter();
        viewModel.getFileListLiveData().observe(this, new Observer<List<File>>() {
            @Override
            public void onChanged(List<File> files) {
                Log.d(TAG, "onChanged: -> getFileListLiveData()");
                fileAdapter.setFileList(files); //reload ui
            }
        });

        viewModel.getCurrentFolderPath_LiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String currentFilePath) {
                Log.d(TAG, "onChanged: ");
                //Path of the current folder has changed, so reload UI and display contents
                viewModel.getFiles(currentFilePath);
            }
        });
    }

    public void setupRecyclerview(){
        fileRecyclerview = findViewById(R.id.fileRecyclerview);
        fileRecyclerview.setAdapter(fileAdapter);
        fileRecyclerview.setLayoutManager(new GridLayoutManager(this,4));
    }

}