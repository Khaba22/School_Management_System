package com.example.lmsshine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    RecyclerView mRecyclerView;
    private Context mContext;
    private final List<Upload> mUploads;
    ArrayList<String> urls = new ArrayList<>();

    public ContentAdapter(RecyclerView recyclerView, Context context, List<Upload> mUploads, ArrayList<String> urls) {
        this.mRecyclerView = recyclerView;
        this.mContext = context;
        this.mUploads = mUploads;
        this.urls = urls;
    }

    @NonNull
    @Override
    // create views for recycler view items
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(mContext).inflate(R.layout.content_items, parent, false);
         return new ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        // initialise elements of each items
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        //Glide.with(holder.itemView.getContext()).load(uploadCurrent.getImageUrl()).fitCenter().into(holder.imageView);
        Glide.with(mContext).load(uploadCurrent.getImageUrl()).placeholder(R.drawable.ic_launcher_foreground).fitCenter().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        public  TextView textViewName;
        public ImageView imageView;

        public  ContentViewHolder (View itemView) { // represent individual list items
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int position = mRecyclerView.getChildLayoutPosition( v );
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW); // denotes view action
                    intent.setData(Uri.parse(urls.get(position)));
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
