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
                dumpImageMetaData(uri);
                openFile(uri);

            }
        }
    }

    public void openFile(Uri uri){
        Log.e(TAG, "openFile: uri path ->"+uri.getPath());
        //Get MIMEType
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = "application/"+mime.getExtensionFromMimeType(getActivity().getContentResolver().getType(uri));
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

    public void dumpImageMetaData(Uri uri) {
        Log.d(TAG, "dumpImageMetaData: ");
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i(TAG, "Size: " + size);
            }
        } finally {
            cursor.close();
        }
    }
}
