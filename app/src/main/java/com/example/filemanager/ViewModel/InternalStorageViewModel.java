package com.example.filemanager.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filemanager.Async.LocalExecutors;
import com.example.filemanager.Data.InternalStorageRepository.InternalStorageRepository;

import java.io.File;
import java.util.List;

public class InternalStorageViewModel extends ViewModel {
    private static final String TAG = "InternalStorageViewMode";

    InternalStorageRepository repository;
    MutableLiveData<List<File>> fileListLiveData;
    String currentFolderPath;

    public InternalStorageViewModel(InternalStorageRepository repository, String homeDirectoryPath) {
        Log.d(TAG, "InternalStorageViewModel: ");
        fileListLiveData = new MutableLiveData<>();
        this.repository = repository;
        currentFolderPath =homeDirectoryPath;
    }
    public void setCurrentFolderPath(String path){
        getFiles(path);
        currentFolderPath=path;
    }

    public String getCurrentFolderPath(){
        return currentFolderPath;
    }



    public MutableLiveData<List<File>> getFileListLiveData() {
        Log.d(TAG, "getFileListLiveData: ");
        return fileListLiveData;
    }

    private void getFiles(final String path){
        Log.d(TAG, "getFiles: ");
        LocalExecutors.getInstance().singleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<File> files = repository.getFiles(path);
                Log.d(TAG, "returned files -> "+files.toString());
                LocalExecutors.getInstance().UIThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        fileListLiveData.setValue(files);
                    }
                });
            }
        });
    }



}
