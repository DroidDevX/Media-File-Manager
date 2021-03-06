package com.example.filemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.filemanager.Adapters.InternalStorageAdapter;
import com.example.filemanager.Data.InternalStorageRepository.InternalStorageRepository;
import com.example.filemanager.R;
import com.example.filemanager.ViewModel.InternalStorageViewModel;

import java.io.File;
import java.util.List;

public class FolderActivity extends AppCompatActivity implements InternalStorageAdapter.OnClickListener {
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

    public void setupViewmodel(String path){
        viewModel = new InternalStorageViewModel(InternalStorageRepository.getInstance(),path);
        fileAdapter = new InternalStorageAdapter();
        fileAdapter.setOnClickListener(this);
        viewModel.getFileListLiveData().observe(this, new Observer<List<File>>() {
            @Override
            public void onChanged(List<File> files) {
                Log.d(TAG, "onChanged: -> getFileListLiveData()");
                fileAdapter.setFileList(files); //reload ui
            }
        });

        viewModel.setCurrentFolderPath(path);
    }

    public void setupRecyclerview(){
        fileRecyclerview = findViewById(R.id.fileRecyclerview);
        fileRecyclerview.setAdapter(fileAdapter);
        fileRecyclerview.setLayoutManager(new GridLayoutManager(this,4));
    }

    @Override
    public void onFileClick(File f) {
        if(f.isDirectory())
            viewModel.setCurrentFolderPath(f.getAbsolutePath());
    }

    /**
     * If parent directory is the home directory go back to HomeActivity using onBackPressed, else
     * reload the UI with the parent directory contents
     * */
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        File currentDirectory = new File (viewModel.getCurrentFolderPath());
        if(currentDirectory.getParent()==null
            ||currentDirectory.getParent().equals(Environment.getExternalStorageDirectory().getAbsolutePath())
        )
            super.onBackPressed();
        else
            viewModel.setCurrentFolderPath(currentDirectory.getParent());

    }
}