package com.example.filemanager.ViewModel;

import android.app.Application;
import android.util.SparseIntArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filemanager.Data.MediaStore.BaseMediaStore;
import com.example.filemanager.Data.MediaStore.MediaFile;
import com.example.filemanager.Data.MediaStore.MediaRepository;

import java.util.List;

public class MediaViewModel extends ViewModel {
    private static final String TAG = "SharedFilesViewModel";

    public static final int MEDIA_FOLDER_AUDIO=0;
    public static final int MEDIA_FOLDER_VIDEO=1;
    public static final int MEDIA_FOLDER_IMAGE=2;
    public static final int MEDIA_FOLDER_DOC=3;


    MutableLiveData<List<MediaFile>> filesLiveData;
    private SparseIntArray mediaStoreTypeMap;
    private MediaRepository repository;

    public MediaViewModel(Application application){
        this.filesLiveData = new MutableLiveData<>();
        mediaStoreTypeMap = new SparseIntArray();
        mediaStoreTypeMap.put(MEDIA_FOLDER_AUDIO,MediaRepository.AUDIO);
        mediaStoreTypeMap.put(MEDIA_FOLDER_VIDEO, MediaRepository.VIDEO);
        mediaStoreTypeMap.put(MEDIA_FOLDER_IMAGE, MediaRepository.IMAGE);
        mediaStoreTypeMap.put(MEDIA_FOLDER_DOC, MediaRepository.DOC);
        this.repository = MediaRepository.getInstance(application);
    }

    public MutableLiveData<List<MediaFile>> getFilesLiveData() {
        return filesLiveData;
    }

    /**
     <b>Description</b> <br/>
    * Finds the files of a given target folder. <br/>
     Use static string constants provided by this class(SharedFilesViewModel) as the method argument <br/><br/>

     <b>Post condition</b></br>
     Result is set as LiveData get files by invoking getFilesLiveData()
     * @param mediaFolder Target folder in shared storage (Audio, Video, Image, Document and downloads)
     * */
    public void getFiles(int mediaFolder){

    }


}
