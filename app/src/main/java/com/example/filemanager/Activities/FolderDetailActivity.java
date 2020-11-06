package com.example.filemanager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filemanager.Adapters.FileItemAdapter;
import com.example.filemanager.CustomViews.CustomToolbar;
import com.example.filemanager.CustomViews.FileItemOptionsMenu;
import com.example.filemanager.Data.FileItemModel.FileItem;
import com.example.filemanager.R;
import com.example.filemanager.ViewModel.SharedFilesViewModel;

import java.util.List;
import java.util.Locale;

public class FolderDetailActivity extends AppCompatActivity {
    private static final String TAG = "FolderDetailActivity";
    //Constants
    private static String APPBAR_TITLE_DEFAULT="";
    public  static  int     DEFAULT_APPBAR_ICON_RESOURCE_ID =-1;
    public  static  final String INTENT_EXTRA_TARGET_FOLDER ="target_folder";
    public  static  final String INTENT_EXTRA_APPBAR_ICON ="appbar_icon";

    public static final String AUDIO_FOLDER="audio_folder";
    public static final String VIDEO_FOLDER="video_folder";
    public static final String DOCUMENT_FOLDER="document_folder";
    public static final String IMAGE_FOLDER="image_folder";
    public static final int    STATUS_OK=200;
    public static final int    STATUS_FOLDER_NOT_EXIST=201;

    //Appbar
    CustomToolbar appBar;
    FileItemOptionsMenu fileItemOptionsMenu;


    //Recyclerview
    FileItemAdapter fileAdapter;
    RecyclerView fileRecyclerView;
    SharedFilesViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);
        setupCustomAppbar();
        setupFileItemOptionsMenu();
        setupFilesRecyclerView();
        setupFilesViewModel();

        //TODO
        /*
Design classes that will make querying data from the shared media store much easier
----------------------------------------------------
1) FileItem (Interface)
    - fileURI :String (file path)
    - toString
    + getURI()
    + openFile() - Use implicit intents to open the file , images are opened using gallery, video is opened using
    		   default media player etc..

2)  BaseSharedStorage <FileType> (interface)
  + getFiles() : List<FileType>

Provide the implementation details of getFiles() for every subclass of BaseShared storage to
query List of media files from a shared media folder based on the target folder
-------------------------------------------------------------------------------------
https://developer.android.com/training/data-storage/shared/media#query-collection

Figure out from which public directory do you want
to query data from (Audio, video,download folder etc...)

Define content provider query arguments
  Get Mediastore projection params (store in an array)
  Define selection params (if needed. For video it could be
  duration - leave null if none)
  Define selection args
  Define query sort order

Use try-catch block to query the content provider that provides
access to these shared folders. Provide the correct MediaStore
content URI to the content resolver.

  //Cache column indeices - whatever that means
  //Get values from you query
   //id, name, duration, size, content uri
   //content uri is retreived from the id value

Create the class instance from the queried values.

3) VideoSharedStorage extends BaseSharedStorage <Video>
  + getFiles() : List<FileType>

  ...Do the same for Audio, Downloads,images and documents

4) Create SharedStorage class
    mVideoSharedStorage
    mImageSharedStorage
    mDownloadsSharedStorage
    ...etc

    (Each storage instance is storage in a Hashmap)
     Hashmap<Integer,BaseSharedStorage>;
     Integer key used are static constants used to identify
     the storage

    final int STORAGE_VIDEO=0;
    final int STORAGE_AUDIO=1;
    ...

    (Shared storage class is a singleton, and has Context)

    Create method to getStorageType(int storageType)
    BaseSharedStorage getStorage(int storageType)
      return mStorageMap.get(storageType)

5) Use shared Storage

   BaseSharedStorage videoSharedStorage = SharedStorage.getInstance().getStorage(VIDEO_STORAGE);
   List<FileItem> videos = videoSharedStorage.getFiles();

   //Set live data in viewmodel  - mfilesLiveData.setValue(videos)


For each FileItem received determine the icon to use: (FileAdapter)
------------------------------------------------------------------------------------
public int getFileIcon (FileItem item)
{
   if(item instance of Video)
     return R.id.icon_video); etc
}
*/
    }

    public void setupCustomAppbar(){

        appBar = findViewById(R.id.toolbar);

        DEFAULT_APPBAR_ICON_RESOURCE_ID = getIntent().getIntExtra(INTENT_EXTRA_APPBAR_ICON,-1);
        Glide.with(this).load(ResourcesCompat.getDrawable(getResources(),DEFAULT_APPBAR_ICON_RESOURCE_ID,null))
                                .into(appBar.iconView());

        String targetFolder = getIntent().getStringExtra(INTENT_EXTRA_TARGET_FOLDER);
        if(targetFolder!=null)
            switch (targetFolder){
                case AUDIO_FOLDER : APPBAR_TITLE_DEFAULT ="Audio"; break;
                case VIDEO_FOLDER : APPBAR_TITLE_DEFAULT ="Video"; break;
                case IMAGE_FOLDER : APPBAR_TITLE_DEFAULT ="Image"; break;
                case DOCUMENT_FOLDER: APPBAR_TITLE_DEFAULT="Documents";break;
            }

        appBar.setText(APPBAR_TITLE_DEFAULT);
    }

    public void setupFileItemOptionsMenu(){
        fileItemOptionsMenu = findViewById(R.id.fileItemOptionsMenu);
        fileItemOptionsMenu.setVisibility(View.GONE);
        fileItemOptionsMenu.setEventListener(new FileItemOptionsMenu.EventListener() {
            @Override
            public void onDeleteBtnClicked(View view) {

            }
        });
    }


    public void setupFilesRecyclerView(){
        fileAdapter = new FileItemAdapter(null);
        fileAdapter.addItemEventListener(new FileItemAdapter.ItemEventListener() {
            @Override
            public void onLongClick(FileItem item, int itemPos) {
                fileAdapter.setSelected(itemPos);
                appBar.setText("File selected at pos: "+ itemPos);
                fileItemOptionsMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onClick(FileItem item, int itemPos) {
                fileAdapter.setSelected(FileItemAdapter.SELECTED_NONE);
                appBar.setText(APPBAR_TITLE_DEFAULT);
                fileItemOptionsMenu.setVisibility(View.GONE);
            }
        });
        fileRecyclerView = findViewById(R.id.fileRecyclerview);
        fileRecyclerView.setAdapter(fileAdapter);
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setupFilesViewModel(){
         viewModel = new SharedFilesViewModel();
         String targetFolder = getIntent().getStringExtra(INTENT_EXTRA_TARGET_FOLDER);

         if(targetFolder==null){
             setResult(STATUS_FOLDER_NOT_EXIST);
             finish();
         }
         viewModel.getFilesLiveData().observe(this, new Observer<List<FileItem>>() {
                    @Override
                    public void onChanged(List<FileItem> files) {
                        fileAdapter.setFiles(files);
                    }
                });

         viewModel.getFilesInFolder(targetFolder);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        int lastSelectedItemPos = savedInstanceState.getInt(FileItemAdapter.BUNDLE_ARG_LAST_SELECTED_ITEM_POS);
        fileAdapter.setSelected(lastSelectedItemPos);

        if(fileAdapter.itemIsSelected()) {
            appBar.setText(String.format(Locale.ENGLISH,"File selected at pos: %d",fileAdapter.getSelectedItemPos()+1));
            fileItemOptionsMenu.setVisibility(View.VISIBLE);
        }
        else {
            appBar.setText("Simple File Manager");
            fileItemOptionsMenu.setVisibility(View.GONE);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(FileItemAdapter.BUNDLE_ARG_LAST_SELECTED_ITEM_POS, fileAdapter.getSelectedItemPos());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        setResult(STATUS_OK);
        finish();
    }


}