package com.example.filemanager.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SquareViewGroup extends LinearLayout {
    public SquareViewGroup(Context context) {
        super(context);
    }

    public SquareViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); //width and height the same
    }
}
