package com.franq.dairy.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.R;
import com.franq.dairy.View.Fragments.NoteFragment;

import java.util.List;

public class NoteRealmRecyclerViewAdapter extends RecyclerView.Adapter <NoteRealmRecyclerViewAdapter.MyViewHolder> {

    private NoteFragment.OnListFragmentInteractionListener mListener;
    private List <Note> noteList;

    public NoteRealmRecyclerViewAdapter(List <Note> noteList, NoteFragment.OnListFragmentInteractionListener listFragmentInteractionListener) {
        this.noteList = noteList;
        mListener = listFragmentInteractionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) )
                .inflate( R.layout.fragment_note, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteList.get( position );
        holder.mItem = note;
        holder.mTimeView.setText( parseDateString( note.getDate( ) ) );
        holder.mContentView.setText( note.getTitle( ) );

        holder.mView.setOnClickListener( click -> {
            if ( null != mListener ) {
                mListener.onNoteItemLick( holder.mItem );
            }
        } );
    }

    @Override
    public int getItemCount() {
        if ( noteList == null )
            return 0;
        else
            return noteList.size( );
    }

    public void updateNoteList(List <Note> noteList) {
        if ( this.noteList == null ) {
            this.noteList = noteList;
        } else {
            this.noteList.clear( );
            this.noteList.addAll( noteList );
            notifyDataSetChanged( );
        }
    }

    private String parseDateString(String s) {
        String time = s.substring( s.indexOf( ' ' ), s.length( ) - 3 );
        return time;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTimeView;
        public final TextView mContentView;
        public Note mItem;

        public MyViewHolder(View view) {
            super( view );
            mView = view;
            mTimeView = view.findViewById( R.id.item_number );
            mContentView = view.findViewById( R.id.content );
        }
    }

}
