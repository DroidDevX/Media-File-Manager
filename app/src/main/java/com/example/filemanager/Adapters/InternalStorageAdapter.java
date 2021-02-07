package com.example.filemanager.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filemanager.R;

import java.io.File;
import java.util.List;

public class InternalStorageAdapter extends RecyclerView.Adapter<InternalStorageAdapter.Viewholder> {
    private static final String TAG = "InternalStorageAdapter";
    //Display mode
    public final static int GRIDLAYOUT_MODE=0;
    public final static int LINEARLAYOUT_MODE=1;
    private int CURRENTLAYOUT_MODE;

    OnClickListener listener;
    List<File> fileList;


    public InternalStorageAdapter() {
        CURRENTLAYOUT_MODE =GRIDLAYOUT_MODE;
    }

    public interface OnClickListener{
        void onFileClick(File f);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener= listener;
    }

    public void setFileList(List<File> fileList){
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    public void setLayoutmode(int mode){
        CURRENTLAYOUT_MODE = (mode==GRIDLAYOUT_MODE) ? GRIDLAYOUT_MODE:LINEARLAYOUT_MODE;
    }

    public int getLayoutmode(){
        return CURRENTLAYOUT_MODE;
    }

    static class Viewholder extends RecyclerView.ViewHolder
    {
        protected ImageView iconImageView;
        protected TextView titleTextView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
        public void bindData(File f){
            titleTextView.setText(f.getName());
            loadIcon(f);
        }

        void loadIcon(File f){
            Context c = itemView.getContext();
            int iconID = (f.isDirectory()) ? R.drawable.icon_folder : R.drawable.icon_plain_file;
            Glide.with(c).load(iconID).fitCenter().into(iconImageView);
        }
    }

    static class GridLayoutViewholder extends Viewholder{
        public GridLayoutViewholder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.gridlayout_Imageview);
            titleTextView = itemView.findViewById(R.id.gridlayout_TitleTV);
        }
    }

    static class LinearLayoutViewholder extends Viewholder{
        public LinearLayoutViewholder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.linearlayout_ImageView);
            titleTextView = itemView.findViewById(R.id.linearlayout_TitleTV);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return CURRENTLAYOUT_MODE;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d(TAG, "onCreateViewHolder: layoutmode->");
        if(viewType ==GRIDLAYOUT_MODE) {
            Log.d(TAG, " gridlayoutmode");
            return new GridLayoutViewholder(inflater.inflate(R.layout.gridlayout_internalstorage_fileitem, parent, false));
        }
        else {
            Log.d(TAG, " linearlayoutmode ");
            return new LinearLayoutViewholder(inflater.inflate(R.layout.linearlayout_internalstorage_fileitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.bindData(fileList.get(position));
        if(listener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFileClick(fileList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (fileList ==null|| fileList.size()==0)? 0: fileList.size();
    }

}
