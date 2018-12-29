package com.franq.dairy.View.Adapters;

import android.support.annotation.NonNull;

import com.franq.dairy.Utility.ImagesHepler;

import java.util.ArrayList;

public class ImageRecyclerViewAdapter extends CreateImageRecyclerViewAdapter {

    private ImageClickListener listener;

    public ImageRecyclerViewAdapter(ArrayList<String> uri, ImageClickListener listener) {
        super( uri );
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stringURI = uri.get( position );
        ImagesHepler.setImages( stringURI, holder.iv, true);
        holder.view.setOnClickListener( click -> listener.onImageClick( stringURI ));
    }

    public interface ImageClickListener{
        void onImageClick(String uri);
    }

}
