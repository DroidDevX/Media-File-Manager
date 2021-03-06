package com.example.filemanager.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import java.io.File;
import java.util.Date;

public class FilePropertiesDialog extends DialogFragment {
    private static final String TAG = "FilePropertiesDialog";
    private static String DIALOG_MESSAGE_ARG ="init_arguments";


    public static FilePropertiesDialog createDialogFromFile(File file){
        Log.d(TAG, "createDialogFromFile: ");
        FilePropertiesDialog dialog = new FilePropertiesDialog();
        Bundle args = new Bundle();
        String filePath = file.getAbsolutePath();
        String fileType;
        if(!file.isDirectory()){
            fileType  = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        }
        else
            fileType = "Directory";

        String fileProperties = new StringBuilder().append("File Properties \n\n")
                                .append("Title: ").append(file.getName()).append("\n")
                                .append("Date modified: ").append(new Date(file.lastModified())).append("\n")
                                .append("Size: ").append(file.length()/1024).append("KB \n")
                                .append("Type: ").append(fileType).toString();

        args.putString(DIALOG_MESSAGE_ARG,fileProperties);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if(getArguments()==null || getArguments().getString(DIALOG_MESSAGE_ARG)==null) {
            Log.d(TAG, "Bundle Arguments is null!");

        }

        else {
            Log.d(TAG, "Creating alert dialog...");
            if(getActivity()!=null)
                new AlertDialog.Builder(getActivity())
                    .setMessage(getArguments().getString(DIALOG_MESSAGE_ARG))
                    .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dismiss();
                        }
                    }).create().show();
        }
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach: ");
    }
}
