package com.example.filemanager.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.filemanager.R;

/**
 * Custom view menu that contains controls for manipulating FileItem instances
 * */
public class FileItemOptionsMenu extends LinearLayout {
    ImageButton deleteFileBtn;
    EventListener listener;

    public interface EventListener{
        void onDeleteBtnClicked(View view);
    }

    public FileItemOptionsMenu(@NonNull Context context) {
        super(context);
    }

    public FileItemOptionsMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public FileItemOptionsMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);

    }

    public void inflateLayout(Context c){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fileitem_options_menu_layout,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        deleteFileBtn = this.findViewById(R.id.deleteFileBtn);
        deleteFileBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteBtnClicked(v);
            }
        });

    }

    public void setEventListener(EventListener listener){
        this.listener = listener;
    }
}
