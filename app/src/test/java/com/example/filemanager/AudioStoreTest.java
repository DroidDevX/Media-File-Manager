package com.example.filemanager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.filemanager.Data.MediaStore.AudioStore.AudioStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AudioStoreTest {

    @Mock
    ContentResolver contentResolver;
    @Mock
    Context appcontext;
    AudioStore audioStore;

    @Before
    public void setupTest(){
        MockitoAnnotations.initMocks(this);
        audioStore = new AudioStore(appcontext);
    }

    @Test
    public void firstTest(){
        //Content provider query params
        Uri contentUri = Uri.parse("");
        String[] projectionColumns ={}; //can be anything
        String whereClause ="";
        String[] selectionArgs ={};
        audioStore.getFiles();

    }

}
