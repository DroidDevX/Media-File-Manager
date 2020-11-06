package com.example.filemanager.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.filemanager.R;

/**
 * Custom toolbar that can be placed within an appbar <br/><br/>
 * <b>Note: </b> This view is not a support action bar.
 */
public class CustomToolbar extends LinearLayout {
    private static final String TAG = "CustomToolbar";
    ImageView toolbarIconView;
    TextView toolbarTextView;

    public CustomToolbar(@NonNull Context context) {
        super(context);
        Log.d(TAG, "CustomToolbar(): 1");
        inflateLayout(context);
    }

    public CustomToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "CustomToolbar(): 2");
        inflateLayout(context);
    }

    public CustomToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "CustomToolbar(): 3");
        inflateLayout(context);

    }

    public void inflateLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_toolbar,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG,"onFinishInflate()");
        toolbarTextView = this.findViewById(R.id.toolBarTitleTV);
        toolbarIconView = this.findViewById(R.id.toolBarIconview);
    }


    public void setText(String titleText){
        Log.d(TAG, "setText: titleText");
        toolbarTextView.setText(titleText);
    }

    /**
     * Returns an image view within the toolbar. Use this view to assign icons to the toolbar
     * */
    public ImageView iconView(){
        return toolbarIconView;
    }

}
