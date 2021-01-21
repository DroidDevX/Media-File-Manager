package com.example.filemanager.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filemanager.Data.MediaStore.BaseMediaFile;
import com.example.filemanager.R;

import java.util.List;

public class MediaFileAdapter extends RecyclerView.Adapter<MediaFileAdapter.ViewHolder> {
    private static final String TAG = "FileItemAdapter";
    public static final String BUNDLE_ARG_LAST_SELECTED_ITEM_POS="selectedItemPOS";

      //If Position of selected file is -1, its not selected
    public static final int FILE_NOT_LONG_CLICKED =-1;
    private int default_icon_resID=-1;


    /**
    Alerts subscribers when the user selects a file  from the FileItemAdapter (through its the Recyclerview)
    **/
    public interface ItemEventListener {
         void onLongClick(BaseMediaFile item, int itemPos);
         void onClick(BaseMediaFile item, int itemPos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImageView;
        TextView fileTitleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileTitleTV = itemView.findViewById(R.id.fileTitleTV);
            iconImageView = itemView.findViewById(R.id.iconImageview);
        }

        public void bindData(BaseMediaFile item)
        {
            fileTitleTV.setText(item.getName());

        }
    }


    ItemEventListener itemEventListener;
    List<BaseMediaFile> fileList;
    int longClickFilePos;

    public MediaFileAdapter(List<BaseMediaFile> fileList)
    {
        this.fileList = fileList;
        this.longClickFilePos = FILE_NOT_LONG_CLICKED;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout_mediafile_viewholder,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindData(fileList.get(position));
        if(defaultIconIsSet())
            Glide.with(holder.iconImageView.getContext()).load(default_icon_resID).into(holder.iconImageView);

        if(itemEventListener !=null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickFilePos = position;
                    itemEventListener.onLongClick(fileList.get(position),position);
                    return true;
                }
            });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEventListener.onClick(fileList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
            return (fileList!=null) ? fileList.size():0;
    }

    public void addItemEventListener(ItemEventListener listener)
    {
        this.itemEventListener = listener;
    }


    /**
    * Determines if a file in the adapter was  selected by the user with a long-click
    * **/
    public boolean fileIsLongClicked(){
        return longClickFilePos >-1 &&  longClickFilePos < fileList.size();
    }

    public int getLongClickedPos(){
        return longClickFilePos;
    }

    /**
     * Marks an file in the adapter as selected when it was long clicked
     * */
    public void setLongClickSelected(int itemPos){

        longClickFilePos = (fileList==null || fileList.size()==0||itemPos <0 || itemPos >= fileList.size())
                ? FILE_NOT_LONG_CLICKED :itemPos;
    }

    public void setFiles(List<BaseMediaFile> files){
        this.fileList = files;
    }

    public void setDefaultIcon(int resID){
        Log.e(TAG, "setDefaultIcon: " );
        default_icon_resID=resID;
    }

    public boolean defaultIconIsSet(){
        return default_icon_resID!=-1;
    }
    
    public BaseMediaFile getLongClickedFile(){
        return (longClickFilePos==FILE_NOT_LONG_CLICKED)? null: fileList.get(longClickFilePos);
    }
    

}
