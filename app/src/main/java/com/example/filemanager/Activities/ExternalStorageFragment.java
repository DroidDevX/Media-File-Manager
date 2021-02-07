package com.example.filemanager.Activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filemanager.Adapters.SharedFoldersAdapter;
import com.example.filemanager.Data.SharedFolderModel.SharedFolder;
import com.example.filemanager.R;
import com.example.filemanager.Util.AppChooserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays media folders that are publicly accessible to all apps on the Android device.
 * Namely : Audio, Image, Video and Documents
 * */
public class ExternalStorageFragment extends Fragment {
    private static final String TAG = "SharedFoldersActivity";

    RecyclerView fileRecyclerView;
    SharedFoldersAdapter fileAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_external_storage,container,false);
        fileAdapter = new SharedFoldersAdapter(createDefaultFolders());
        setupRecyclerView(view);
        return view;
    }

    public List<SharedFolder> createDefaultFolders(){
        List<SharedFolder> folders = new ArrayList<>();
        folders.add(new SharedFolder("Audio", R.drawable.icon_audio));
        folders.add(new SharedFolder("Video", R.drawable.icon_video));
        folders.add(new SharedFolder("Image", R.drawable.icon_image));
        folders.add(new SharedFolder("Document", R.drawable.icon_document));
        return folders;
    }

    public void setupRecyclerView(View fragmentView){

        fileAdapter.setOnClickListener(new SharedFoldersAdapter.FolderOnClickListener() {
            @Override
            public void OnClick(SharedFolder folder, int position) {

                if(folder.getIconID()==R.drawable.icon_document)
                    createFilePickerAlertDialog();
                else{
                    Intent i = new Intent(getActivity(),MediaFolderActivity.class);
                    i.putExtra(MediaFolderActivity.INTENT_EXTRA_APPBAR_ICON,folder.getIconID());
                    startActivity(i);
                }
            }
        });

        fileRecyclerView = fragmentView.findViewById(R.id.fileRecyclerview);
        fileRecyclerView.setAdapter(fileAdapter);
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
    }

    public static final int REQUEST_DOCUMENT_FILE=100;
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
    public void openDocumentFilePicker(){
        if(!isExternalStorageReadable())
            return;

        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(getActivity().getApplicationContext(), null);
        File primaryExternalStorage = externalStorageVolumes[0];


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/*"); //can choose any file type , otherwise use e.g "application/pdf"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads. (Min API 26)
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, (Uri.parse(primaryExternalStorage.getAbsolutePath())));

        startActivityForResult(intent, REQUEST_DOCUMENT_FILE);
    }
    public void createFilePickerAlertDialog(){
        String message ="The system file picker will now open. Select a document from the picker to proceed. You will then be prompted with" +
                " an app-chooser to select an app to open the selected file.";
        new AlertDialog.Builder(getActivity())
                .setTitle("Open Document")
                .setMessage(message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            openDocumentFilePicker();

                    }
                })
        .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {

        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode,resultCode,resultData);
        if (requestCode == REQUEST_DOCUMENT_FILE
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                // Perform operations on the document using its URI.
                Log.e(TAG,"Result URI: "+uri.toString());
                if(getContext()!=null)
                    AppChooserUtil.openFile(getContext(),uri);

            }
        }
    }


}
