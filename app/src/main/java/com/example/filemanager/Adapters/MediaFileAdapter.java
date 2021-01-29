package com.example.filemanager.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    static abstract class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fileImageView;
        TextView fileTitleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setThumbnail(BaseMediaFile item){
            Glide.with(itemView.getContext()).load(item.getUri()).into(fileImageView);
        }
        public void setThumbnail(int resID){
            Glide.with(itemView.getContext()).load(resID).into(fileImageView);
        }
        public void bindData(BaseMediaFile item){
            fileTitleTV.setText(item.getName());
        }
    }

    static class LinearLayoutViewHolder extends ViewHolder{
        public LinearLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImageView = itemView.findViewById(R.id.fileItem_linearlayout_ImageView);
            fileTitleTV = itemView.findViewById(R.id.fileitem_linearlayout_TitleTV);
        }

    }

    static class GridLayoutViewHolder extends ViewHolder {
        public GridLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImageView = itemView.findViewById(R.id.fileitem_gridlayout_Imageview);
            fileTitleTV = itemView.findViewById(R.id.fileitem_gridlayout_TitleTV);
        }
    }

    ItemEventListener itemEventListener;
    List<BaseMediaFile> fileList;
    int longClickFilePos;
    public static final int LINEAR_LAYOUT=0;
    public static final int GRID_LAYOUT=1;
    private int currentLayout;

    public MediaFileAdapter(List<BaseMediaFile> fileList, int layoutMode)
    {
        this.fileList = fileList;
        this.longClickFilePos = FILE_NOT_LONG_CLICKED;
        this.currentLayout= (layoutMode==LINEAR_LAYOUT) ? LINEAR_LAYOUT:GRID_LAYOUT;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType: "+viewType);
        if(viewType==LINEAR_LAYOUT) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout_mediafile_viewholder, parent, false);
            return new LinearLayoutViewHolder(itemView);
        }
        else{ //viewType == GRID_LAYOUT
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridlayout_mediafile_item_viewholder, parent, false);
            return new GridLayoutViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindData(fileList.get(position));
        if(defaultIconIsSet())
            holder.setThumbnail(default_icon_resID);

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

    @Override
    public int getItemViewType(int position) {
        return currentLayout;
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
    public void setLayoutMode(int layoutMode){
        currentLayout = (layoutMode==LINEAR_LAYOUT) ? LINEAR_LAYOUT:GRID_LAYOUT;
    }

    public boolean isGridMode(){
        return currentLayout==GRID_LAYOUT;
    }



}
