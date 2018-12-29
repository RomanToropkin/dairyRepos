package com.franq.dairy.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class CreateImageRecyclerViewAdapter extends RecyclerView.Adapter <CreateImageRecyclerViewAdapter.ViewHolder> {

    ArrayList <String> uri;

    public CreateImageRecyclerViewAdapter(ArrayList <String> uri) {
        this.uri = uri;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) )
                .inflate( R.layout.add_image_list, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stringURI = uri.get( position );
        if ( stringURI.contains( PreferencesData.baseURL ) ) {
            Picasso.get( )
                    .load( stringURI )
                    .resize( 200, 200 )
                    .centerCrop( )
                    .into( holder.iv );
        } else {
            File file = new File( uri.get( position ) );
            Picasso.get( )
                    .load( file )
                    .resize( 200, 200 )
                    .centerCrop( )
                    .into( holder.iv );
        }
        holder.view.setOnClickListener( click -> {
            deleteImage( position );
        } );
    }

    @Override
    public int getItemCount() {
        return uri.size( );
    }

    private void deleteImage(int id) {
        uri.remove( id );
        notifyItemRemoved( id );
        notifyDataSetChanged( );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView iv;
        public final View view;

        public ViewHolder(View view) {
            super( view );
            this.view = view;
            iv = view.findViewById( R.id.ivAdd );
        }
    }

}
