package com.example.filemanager.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filemanager.Adapters.InternalStorageAdapter;
import com.example.filemanager.CustomViews.FilePropertiesDialog;
import com.example.filemanager.Data.InternalStorageRepository.InternalStorageRepository;
import com.example.filemanager.R;
import com.example.filemanager.Util.AppChooserUtil;
import com.example.filemanager.ViewModel.InternalStorageViewModel;

import java.io.File;
import java.util.List;

public class InternalStorageFragment extends Fragment implements InternalStorageAdapter.OnClickListener, InternalStorageAdapter.OnLongClickListener, LifecycleOwner {
    private static final String TAG = "InternalStorageFragment";
    private static final String BUNDLE_ARG_LAST_FILE_SELECTED_POS="lastFileSelectedPos";
    int LAST_FILE_SELECTED_POS;
    InternalStorageViewModel viewModel;
    InternalStorageAdapter fileAdapter;
    RecyclerView recyclerView;
    public static ActionMode actionMode;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View rootView = inflater.inflate(R.layout.fragment_internal_storage,container,false);
        if(savedInstanceState!=null)
        {
            Log.e(TAG,"Saved Instance state not null");
            LAST_FILE_SELECTED_POS = savedInstanceState.getInt(BUNDLE_ARG_LAST_FILE_SELECTED_POS);
            Log.e(TAG,"Last selected Pos: " + savedInstanceState.getInt(BUNDLE_ARG_LAST_FILE_SELECTED_POS));
        }
        else {
            Log.e(TAG, "Last file selected pos not set");
            LAST_FILE_SELECTED_POS=-1;
        }
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


        viewModel.getFileListLiveData().observe(lifecycleOwner, new Observer<List<File>>() {
                @Override
                public void onChanged(List<File> fileList) {
                    Log.d(TAG, "onChanged: ");
                    for (File f:fileList) {
                        Log.d(TAG, "onChanged: new file -> "+f.getPath());
                        Log.d(TAG,"File is directory? ->"+f.isDirectory());
                    }
                    fileAdapter.setFileList(fileList);
                    if(LAST_FILE_SELECTED_POS!=-1 && getActivity()!=null){ //file was previously selected, open context menu
                        actionMode = createActionModeCallback(getActivity(),fileList.get(LAST_FILE_SELECTED_POS));
                    }


                }
            });
        viewModel.setCurrentFolderPath(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public void setupFileAdapter(){
        fileAdapter = new InternalStorageAdapter();
        fileAdapter.setOnClickListener(this);
        fileAdapter.setOnLongClickListener(this);
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
    public void onFileClick(File f)
    {
        Log.d(TAG, "onFileClick: ");
        if(actionMode!=null)
        {//Close any context menu if single click is detected
            actionMode.finish();
            actionMode=null;
        }
        //If file f is folder, then display file contents, else f is regular file -> open using app-chooser
        else if(f.isDirectory())
        {
            Intent i = new Intent(getContext(),FolderActivity.class);
            i.putExtra(FolderActivity.ACTION_DISPLAY_DIRECTORY_CONTENTS,f.getAbsolutePath());
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    @Override
    public void onFileLongClick(final File selectedFile, int filePos) {
        if(actionMode!=null)
            return;

        if(getActivity()==null || getContext()==null)
            return;

        actionMode = createActionModeCallback(getActivity(),selectedFile);
        LAST_FILE_SELECTED_POS = filePos;
    }

    ActionMode createActionModeCallback(final Activity activity,final File selectedFile){
        Log.e(TAG, "createActionModeCallback: ");
        return  activity.startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.internal_storage_context_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Log.d(TAG, "onActionItemClicked: ");
                switch (item.getItemId()){
                    case R.id.openwithOption:
                        Log.d(TAG, "onActionItemClicked: , openwithOption");
                        if(!selectedFile.isDirectory()) //file is ordinary file not directory
                            AppChooserUtil.openFile(activity,Uri.parse(selectedFile.getAbsolutePath()));
                        break;
                    case R.id.filePropertiesOption:
                        Log.d(TAG, "onActionItemClicked: , filePropertiesOption");
                        displayFilePropertiesDialog(selectedFile);
                        break;

                    case R.id.toggleLinearLayoutOption:
                        Log.d(TAG, "onActionItemClicked: , toggleLinearLayoutOption");
                        fileAdapter.setLayoutmode(InternalStorageAdapter.LINEARLAYOUT_MODE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        break;

                    case R.id.toggleGridlayoutOption:
                        Log.d(TAG, "onActionItemClicked: , toggleGridlayoutOption");
                        fileAdapter.setLayoutmode(InternalStorageAdapter.GRIDLAYOUT_MODE);
                        recyclerView.setLayoutManager(new GridLayoutManager(activity,4));
                        break;
                    default:
                        return false;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.e(TAG, "onDestroyActionMode: ");
                actionMode =null;
            }
        });
    }
    public void displayFilePropertiesDialog(File file){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.dialogContainer, FilePropertiesDialog.createDialogFromFile(file));
        ft.commit();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
        actionMode =null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_ARG_LAST_FILE_SELECTED_POS,LAST_FILE_SELECTED_POS);
        super.onSaveInstanceState(outState);
    }
}
