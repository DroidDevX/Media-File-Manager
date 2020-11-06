package com.example.filemanager.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filemanager.Data.FileItemModel.FileItem;
import com.example.filemanager.Data.MockData.MockData;

import java.util.List;

public class SharedFilesViewModel extends ViewModel {
    private static final String TAG = "SharedFilesViewModel";


    MutableLiveData<List<FileItem>> filesLiveData;

    public SharedFilesViewModel(){
        this.filesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<FileItem>> getFilesLiveData(){
        return filesLiveData;
    }

    /**
     <b>Description</b> <br/>
    * Finds the files of a given target folder. <br/>
     Use static string constants provided by this class(SharedFilesViewModel) as the method argument <br/><br/>

     <b>Post condition</b></br>
     Result is set as LiveData get files by invoking getFilesLiveData()
     * @param targetFolder Target folder in shared storage (Audio, Video, Image, Document and downloads)
     * */
    public void getFilesInFolder(String targetFolder){
        Log.d(TAG,"getFilesInFolder(), targetFolder- "+targetFolder);
        filesLiveData.setValue(MockData.createRandomFileItems(10));
    }


}
