package com.example.filemanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filemanager.Data.FileItemModel.FileItem;
import com.example.filemanager.R;

import java.util.List;

public class FileItemAdapter extends RecyclerView.Adapter<FileItemAdapter.ViewHolder> {
    private static final String TAG = "FileItemAdapter";
    public static final String BUNDLE_ARG_LAST_SELECTED_ITEM_POS="selectedItemPOS";
    public static final int SELECTED_NONE=-1;


    /**
    Alerts subscribers when the user selects a file item from the FileItemAdapter (through its the Recyclerview)
    **/
    public interface ItemEventListener {
         void onLongClick(FileItem item, int itemPos);
         void onClick(FileItem item, int itemPos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImageView;
        TextView fileTitleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileTitleTV = itemView.findViewById(R.id.fileTitleTV);
            iconImageView = itemView.findViewById(R.id.iconImageview);
        }

        public void bindData(FileItem item)
        {
            fileTitleTV.setText(item.getFileName());
            Glide.with(itemView.getContext()).load(item.getIconResourceID()).into(iconImageView);

        }
    }


    ItemEventListener itemEventListener;
    List<FileItem> fileList;
    int itemSelectedPos;

    public FileItemAdapter(List<FileItem> fileList)
    {
        this.fileList = fileList;
        this.itemSelectedPos=SELECTED_NONE;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_file,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindData(fileList.get(position));
        if(itemEventListener !=null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
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

    public FileItem getSelectedItem(){
        return itemIsSelected() ? fileList.get(itemSelectedPos):null;
    }

    /**
    * Determines if an item in the adapter is selected by the user
    * **/
    public boolean itemIsSelected(){
        return itemSelectedPos>-1;
    }

    public int getSelectedItemPos(){
        return itemSelectedPos;
    }

    /**
     * Marks an item in the adapter as selected
     * */
    public void setSelected(int itemPos){
        itemSelectedPos = (itemPos==SELECTED_NONE) ? -1:itemPos;
    }

    public void setFiles(List<FileItem> files){
        this.fileList = files;
    }
}
