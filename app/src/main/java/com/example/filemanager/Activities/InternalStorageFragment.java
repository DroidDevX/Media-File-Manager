package com.example.filemanager.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filemanager.Adapters.InternalStorageAdapter;
import com.example.filemanager.Data.InternalStorageRepository.InternalStorageRepository;
import com.example.filemanager.R;
import com.example.filemanager.ViewModel.InternalStorageViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageFragment extends Fragment implements InternalStorageAdapter.OnClickListener, LifecycleOwner {
    private static final String TAG = "InternalStorageFragment";
    InternalStorageViewModel viewModel;
    InternalStorageAdapter fileAdapter;
    RecyclerView recyclerView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_internal_storage,container,false);
        setupViewModel();
        setupFileAdapter();
        setupFileRecyclerview(rootView);
        return rootView;
    }

    public void setupViewModel(){
        Log.d(TAG, "setupViewModel: ");
        if(getContext()==null
        )
            return;

        LifecycleOwner lifecycleOwner = this;
        viewModel = new InternalStorageViewModel(InternalStorageRepository.getInstance(),"");
        viewModel.getCurrentFolderPath_LiveData().observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(String currentFolderPath) {
                Log.d(TAG, "onChanged:, getCurrentFolderPath_LiveData() ");
                viewModel.getFiles(currentFolderPath);
            }
        });

        viewModel.getFileListLiveData().observe(lifecycleOwner, new Observer<List<File>>() {
                @Override
                public void onChanged(List<File> fileList) {
                    Log.d(TAG, "onChanged: ");
                    for (File f:fileList) {
                        Log.d(TAG, "onChanged: new file -> "+f.getPath());
                        Log.d(TAG,"File is directory? ->"+f.isDirectory());
                    }
                    fileAdapter.setFileList(fileList);

                }
            });
        viewModel.setCurrentFolderPath(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public void setupFileAdapter(){
        fileAdapter = new InternalStorageAdapter();
        fileAdapter.setOnClickListener(this);
    }

    public void setupFileRecyclerview(View rootview){
        recyclerView = rootview.findViewById(R.id.fileRecyclerview);
        recyclerView.setAdapter(fileAdapter);
        RecyclerView.LayoutManager layoutManager;
        if(fileAdapter.getLayoutmode() == InternalStorageAdapter.GRIDLAYOUT_MODE)
            layoutManager = new GridLayoutManager(getContext(),4);
        else
            layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onFileClick(File f) {
        //If file f is folder, then display file contents, else f is regular file -> open using app-chooser
    }
}
