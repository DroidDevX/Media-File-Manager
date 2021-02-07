package com.example.filemanager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;

import com.example.filemanager.Adapters.MediaFileAdapter;
import com.example.filemanager.CustomViews.FilePropertiesDialog;
import com.example.filemanager.Data.MediaStore.BaseMediaFile;
import com.example.filemanager.Data.MediaStore.MediaRepository;
import com.example.filemanager.R;
import com.example.filemanager.ViewModel.MediaViewModel;

import java.util.List;
import java.util.Locale;

/*
* Purpose: To display contents of a media folder given the uri path from
* getIntent(). Contents will be returned to the view via the viewmodel as instances of
* BaseMediaFile objects. (List<BaseMediaFile>)
*
* */
public class MediaFolderActivity extends AppCompatActivity implements FilePropertiesDialog.DialogOnClickListener{

    private static final String TAG = "FolderDetailActivity";
    //Constants
    private static String DEFAULT_APPBAR_SHAREDFOLDER_TITLE ="";
    public  static  int     DEFAULT_APPBAR_ICON_RESOURCE_ID =-1;
    public  static  final String INTENT_EXTRA_APPBAR_ICON ="appbar_icon";
    

    
    private int SELECTED_FOLDER=-1;
    public static final int    STATUS_OK=200;
    public static final int    STATUS_FOLDER_NOT_EXIST=201;

    RecyclerView.LayoutManager gridLayoutManager;
    RecyclerView.LayoutManager linearLayoutManager;
    MediaFileAdapter fileAdapter;
    RecyclerView fileRecyclerView;
    MediaViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);
        setupActionbar();
        setupFileAdapter();
        setupFilesViewModel();
        setupFilesRecyclerView();

    }

    @Override
    public void fileDialogOnBackClicked() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        //Open with | Properties
         if(fileAdapter.fileIsLongClicked()){
             getMenuInflater().inflate(R.menu.file_item_options_menu,menu);
             //Grid layout and Linear layout toggle option
             if(fileAdapter.isGridMode()) {
                 menu.findItem(R.id.toggleGridlayoutOption).setVisible(false);
                 menu.findItem(R.id.toggleLinearLayoutOption).setVisible(true);
             }
             else
             {
                 menu.findItem(R.id.toggleLinearLayoutOption).setVisible(false);
                 menu.findItem(R.id.toggleGridlayoutOption).setVisible(true);
             }
             return true;
         }
         else
             return super.onCreateOptionsMenu(menu);
         
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()){
            case R.id.openwithOption:
                launchAppChooser(fileAdapter.getLongClickedFile().getUri());
                break;
            case R.id.filePropertiesOption:
                displayFileProperties(fileAdapter.getLongClickedFile());
                break;
            case R.id.toggleGridlayoutOption:
                toggleGridLayout();
                invalidateOptionsMenu();
                break;
            case R.id.toggleLinearLayoutOption:
                toggleLinearLayout();
                invalidateOptionsMenu();
                break;
        }


        return false;
    }
    
    
    
    public void displayFileProperties(BaseMediaFile file){
        Log.d(TAG, "displayFileProperties: "+file.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.dialogContainer,FilePropertiesDialog.createDialogFromFile(file));
        ft.commit();
    }

    public void setupActionbar(){




        DEFAULT_APPBAR_ICON_RESOURCE_ID = getIntent().getIntExtra(INTENT_EXTRA_APPBAR_ICON,-1);


        if(DEFAULT_APPBAR_ICON_RESOURCE_ID!=-1) {
            switch (DEFAULT_APPBAR_ICON_RESOURCE_ID) {
                case R.drawable.icon_audio:
                    DEFAULT_APPBAR_SHAREDFOLDER_TITLE = "Audio";
                    break;
                case R.drawable.icon_video:
                    DEFAULT_APPBAR_SHAREDFOLDER_TITLE = "Video";
                    break;
                case R.drawable.icon_image:
                    DEFAULT_APPBAR_SHAREDFOLDER_TITLE = "Image";
                    break;
                case R.drawable.icon_document:
                    DEFAULT_APPBAR_SHAREDFOLDER_TITLE = "Documents";
                    break;
            }

            setTitle(DEFAULT_APPBAR_SHAREDFOLDER_TITLE);

        }

    }

    public void launchAppChooser(Uri uri){
        Log.d(TAG, "launchAppChooser: "+uri.toString());
        //Get MIMEType
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = "application/"+mime.getExtensionFromMimeType(getContentResolver().getType(uri));
        Log.e(TAG,"MIME TYPE ->:"+type);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setData(uri);

        //Grant URI permissions to apps opening the file
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY| Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    public void setupFileAdapter(){
        Log.d(TAG, "setupFileAdapter: ");
        Context appContext = getApplicationContext();
        fileAdapter = new MediaFileAdapter(null,MediaFileAdapter.GRID_LAYOUT);
        fileAdapter.addItemEventListener(new MediaFileAdapter.ItemEventListener() {
            @Override
            public void onLongClick(BaseMediaFile file, int itemPos) {
                invalidateOptionsMenu();

                if (getSupportActionBar()!=null) {
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    actionBar.setTitle(Html.fromHtml(String.format(Locale.ENGLISH,"<font color =#FFFFFF > %s </font>",DEFAULT_APPBAR_SHAREDFOLDER_TITLE)));
                    actionBar.setSubtitle(Html.fromHtml(String.format(Locale.ENGLISH,"<font color =#FFFFFF > Selected : %s </font>",file.getName())));
                }


                fileAdapter.setLongClickSelected(itemPos);;
            }

            @Override
            public void onClick(BaseMediaFile file, int itemPos) {
                invalidateOptionsMenu();


                if (getSupportActionBar()!=null) {
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorWhite)));
                    actionBar.setTitle(Html.fromHtml(String.format(Locale.ENGLISH,"<font color =#000000 > %s </font>",DEFAULT_APPBAR_SHAREDFOLDER_TITLE)));
                    actionBar.setSubtitle("");
                }



                fileAdapter.setLongClickSelected(MediaFileAdapter.FILE_NOT_LONG_CLICKED);

                //Launch AppChooser
                launchAppChooser(file.getUri());

            }
        });


        SELECTED_FOLDER = getIntent().getIntExtra(INTENT_EXTRA_APPBAR_ICON,-1); //default folder not exist
        switch (SELECTED_FOLDER){

            case R.drawable.icon_audio:
                SELECTED_FOLDER = MediaRepository.AUDIO; break;
            case R.drawable.icon_video:
                SELECTED_FOLDER = MediaRepository.VIDEO;break;
            case R.drawable.icon_image:
                SELECTED_FOLDER = MediaRepository.IMAGE;break;
            default: SELECTED_FOLDER= -1;
            break;


        }

        if(SELECTED_FOLDER== -1){
            setResult(STATUS_FOLDER_NOT_EXIST);
            finish();
        }

    }

    public void setupFilesRecyclerView(){
        Log.d(TAG, "setupFilesRecyclerView: ");
        fileRecyclerView = findViewById(R.id.fileRecyclerview);
        fileRecyclerView.setAdapter(fileAdapter);
        gridLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager = new LinearLayoutManager(this);
        fileRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void setupFilesViewModel(){
         viewModel = new MediaViewModel(getApplication());
         final int defaultIcon = getIntent().getIntExtra(INTENT_EXTRA_APPBAR_ICON,-1);
         viewModel.getFilesLiveData().observe(this, new Observer<List<BaseMediaFile>>() {
             @Override
             public void onChanged(List<BaseMediaFile> mediaFiles) {
                 for(BaseMediaFile f: mediaFiles){
                     String MIMEType = getApplicationContext().getContentResolver().getType(f.getUri());
                     f.setMIMEType(MIMEType);
                 }

                 fileAdapter.setDefaultIcon(defaultIcon);
                 fileAdapter.setFiles(mediaFiles);
                 fileAdapter.notifyDataSetChanged();

             }
         });

         viewModel.getFiles(SELECTED_FOLDER);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        int lastSelectedItemPos = savedInstanceState.getInt(MediaFileAdapter.BUNDLE_ARG_LAST_SELECTED_ITEM_POS);
        fileAdapter.setLongClickSelected(lastSelectedItemPos);

        if(fileAdapter.fileIsLongClicked())
            setTitle(String.format(Locale.ENGLISH, "Selected: %s", fileAdapter.getLongClickedFile().getName()));

        else
            setTitle(DEFAULT_APPBAR_SHAREDFOLDER_TITLE);


        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(MediaFileAdapter.BUNDLE_ARG_LAST_SELECTED_ITEM_POS, fileAdapter.getLongClickedPos());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        setResult(STATUS_OK);
        finish();
    }
    
    public void toggleGridLayout(){
        fileAdapter.setLayoutMode(MediaFileAdapter.GRID_LAYOUT);
        //fileAdapter.notifyDataSetChanged();
        fileRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void toggleLinearLayout(){
        fileAdapter.setLayoutMode(MediaFileAdapter.LINEAR_LAYOUT);
        //fileAdapter.notifyDataSetChanged();
        fileRecyclerView.setLayoutManager(linearLayoutManager);
    }

}