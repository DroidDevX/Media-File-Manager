package com.example.filemanager.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class SplashScreenImage extends AppCompatImageView {

    public SplashScreenImage(Context context) {
        super(context);
    }

    public SplashScreenImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashScreenImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }
}
