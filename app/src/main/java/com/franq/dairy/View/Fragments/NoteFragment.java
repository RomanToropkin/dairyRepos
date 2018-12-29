package com.franq.dairy.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Presenter.PNote.NotePresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Adapters.NoteRealmRecyclerViewAdapter;
import com.franq.dairy.View.Contracts.NotesContractView;

import java.util.List;

/**
 * @see CreatingFragment
 */
public class NoteFragment extends Fragment implements NotesContractView {

    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String NOTE_FRAGMET_VIEW_TAG = "FRAGMENT_VIEW";
    private NotePresenterImpl presenter;
    private RecyclerView recyclerView;

    private NoteRealmRecyclerViewAdapter newAdapter;

    public NoteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(NOTE_FRAGMET_VIEW_TAG, "onCreateView");
        if (mListener != null){
            mListener.onNoteFragmentInteract("Мой дневник");
        }
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetachView();
        recyclerView.setAdapter(null);
        if ( mListener != null )
            mListener.onBlankFragmentStatusChange( false );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSwipeList() {
        Log.d(NOTE_FRAGMET_VIEW_TAG, "isRefreshing");
        presenter.getSynchronizeNotes();
    }


    @Override
    public void onResume() {
        super.onResume( );
        presenter.getSynchronizeNotes( );
    }

    public void changeStatus(boolean flag) {
        if ( mListener != null ) {
            mListener.onBlankFragmentStatusChange( flag );
        }
    }

    @Override
    public void refreshList(List <Note> data) {
        if (data != null) {
            newAdapter.updateNoteList( data );
            if ( data.isEmpty( ) ) {
                changeStatus( true );
            } else
                changeStatus( false );
        } else {
            changeStatus( true );
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing( true );
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing( false );
    }

    @Override
    public void initComponents(View view) {
        presenter = new NotePresenterImpl();
        presenter.onAttachView(this);
        swipeRefreshLayout = view.findViewById( R.id.swiperefresh );
        swipeRefreshLayout.setColorSchemeResources( R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryLight );
        swipeRefreshLayout.setOnRefreshListener( this::onSwipeList );
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        newAdapter = new NoteRealmRecyclerViewAdapter( null, mListener );
        recyclerView.setAdapter( newAdapter );
    }

    public interface OnListFragmentInteractionListener {
        void onNoteItemLick(Note item);

        void onBlankFragmentStatusChange(boolean flag);
        void onNoteFragmentInteract(String name);
    }

    public void setNoteList(List <Note> list) {
        newAdapter.updateNoteList( list );
    }
}
