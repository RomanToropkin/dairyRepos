package com.franq.dairy.View.Adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.R;
import com.franq.dairy.View.Fragments.NoteFragment.OnListFragmentInteractionListener;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class MyNoteRecyclerViewAdapter extends RealmRecyclerViewAdapter<Note, MyNoteRecyclerViewAdapter.ViewHolder> {

    private OnListFragmentInteractionListener mListener;
    private OrderedRealmCollection<Note> items;

    public MyNoteRecyclerViewAdapter(@Nullable OrderedRealmCollection<Note> data, boolean autoUpdate, OnListFragmentInteractionListener mListener) {
        super(data, autoUpdate);
        this.mListener = mListener;
        this.items = getData();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        holder.mIdView.setText(parseDateString(items.get(position).getDate()));
        holder.mContentView.setText(items.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onNoteItemLick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    private String parseDateString(String s){
        return s.substring(s.indexOf(' '));
    }

}
