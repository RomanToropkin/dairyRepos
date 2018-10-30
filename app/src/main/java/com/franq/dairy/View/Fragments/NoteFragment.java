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

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Presenter.PNote.NotePresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Adapters.MyNoteRecyclerViewAdapter;
import com.franq.dairy.View.Contracts.NotesContractView;

import java.util.Arrays;

import io.realm.RealmList;

/**
 * @see CreatingFragment
 */
public class NoteFragment extends Fragment implements NotesContractView {

    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String NOTE_FRAGMET_VIEW_TAG = "FRAGMENT_VIEW";
    private NotePresenterImpl presenter;
    private RecyclerView recyclerView;
    private MyNoteRecyclerViewAdapter adapter;

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
        presenter.closeDB();
        presenter.onDetachView();
        recyclerView.setAdapter(null);
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
        Log.d(NOTE_FRAGMET_VIEW_TAG, Arrays.toString(presenter.getAllNotes().toArray()));
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null)
            mListener.onBlankFragmentStatusChange(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getItemCount() != 0) {
            if (mListener != null)
                mListener.onBlankFragmentStatusChange(false);
        } else {
            mListener.onBlankFragmentStatusChange(true);
        }
    }

    @Override
    public void refreshList(RealmList<Note> data) {
        if (data.size() == 0) {
            if (mListener != null)
                mListener.onBlankFragmentStatusChange(true);
        } else {
            if (mListener != null)
                mListener.onBlankFragmentStatusChange(false);
        }

        adapter.updateData(data);
    }

    @Override
    public void initComponents(View view) {
        presenter = new NotePresenterImpl();
        presenter.onAttachView(this);
        presenter.openDB();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        RealmList<Note> notes = presenter.getList();
//        if (notes.size() != 0){
//            if (mListener != null)
//                mListener.onBlankFragmentStatusChange(false);
//        } else {
//            mListener.onBlankFragmentStatusChange(true);
//        }
        adapter = new MyNoteRecyclerViewAdapter(notes, mListener);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryLight);
        swipeRefreshLayout.setOnRefreshListener(this::onSwipeList);
    }

    public interface OnListFragmentInteractionListener {
        void onNoteItemLick(Note item);

        void onBlankFragmentStatusChange(boolean flag);
        void onNoteFragmentInteract(String name);
    }
}
