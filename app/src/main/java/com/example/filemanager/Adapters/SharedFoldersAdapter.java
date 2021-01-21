package com.example.filemanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filemanager.Data.SharedFolderModel.SharedFolder;
import com.example.filemanager.R;

import java.util.List;

public class SharedFoldersAdapter extends RecyclerView.Adapter<SharedFoldersAdapter.Viewholder> {

    public interface FolderOnClickListener{

        void OnClick(SharedFolder folder, int position);

    }

    static class Viewholder extends RecyclerView.ViewHolder{
        TextView sharedFolderTitleTV;
        ImageView sharedFolderIconView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            sharedFolderTitleTV = itemView.findViewById(R.id.sharedFolderTitleTV);
            sharedFolderIconView = itemView.findViewById(R.id.sharedFolderIconView);
        }

        public void bindData(SharedFolder folder){
            sharedFolderTitleTV.setText(folder.getName());
            Glide.with(itemView.getContext()).load(folder.getIconID()).into(sharedFolderIconView);
        }
    }

    List<SharedFolder> sharedfolders;
    FolderOnClickListener listener;

    public SharedFoldersAdapter(List<SharedFolder> sharedfolders) {
        this.sharedfolders = sharedfolders;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.sharedfolder_viewholder,parent,false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.bindData(sharedfolders.get(position));
        if(listener!=null)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(sharedfolders.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(sharedfolders==null || sharedfolders.size()==0)
            return 0;
        else
            return sharedfolders.size();
    }

    public void setOnClickListener(FolderOnClickListener listener){
        this.listener = listener;
    }


}
