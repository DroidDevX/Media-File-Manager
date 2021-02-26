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

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

public class MediaPropertiesDialog extends DialogFragment {
    private static final String TAG = "FilePropertiesDialog";

    private static String DIALOG_MESSAGE_ARG ="init_arguments";


    public interface DialogOnClickListener{
        void fileDialogOnBackClicked();
    }



    DialogOnClickListener dialogListener;

    public static MediaPropertiesDialog createDialogFromFile(BaseMediaFile file){
        Log.d(TAG, "createDialogFromFile: ");
        MediaPropertiesDialog dialog = new MediaPropertiesDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_MESSAGE_ARG,file.toString());
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
           new AlertDialog.Builder((Context) dialogListener)
                    .setMessage(getArguments().getString(DIALOG_MESSAGE_ARG))
                    .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogListener.fileDialogOnBackClicked();
                            dismiss();
                        }
                    }).create().show();
        }
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach: ");
        if(activity instanceof DialogOnClickListener)
            this.dialogListener = (DialogOnClickListener) activity;


    }

}