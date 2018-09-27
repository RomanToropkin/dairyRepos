package com.franq.dairy.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.franq.dairy.Model.Note;
import com.franq.dairy.Presenter.PNote.NotePresenterImp;
import com.franq.dairy.R;

import java.util.Arrays;
import java.util.List;

import io.realm.OrderedRealmCollection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NoteFragment extends Fragment implements NotesContractView{

    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String NOTE_FRAGMET_VIEW_TAG = "FRAGMENT_VIEW";
    private NotePresenterImp presenter;
    private RecyclerView recyclerView;
    private MyNoteRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onNoteFragmentInteractio("Мой дневник");
        }
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        presenter = new NotePresenterImp();
        presenter.attachFragment(this);
        presenter.openDB();
        initSwipeRefreshLayout(view);
        initRecycler(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachFragment();
        recyclerView.setAdapter(null);
        presenter.closeDB();
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
//        //Запрос на сервер
//        presenter.addNote();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void refreshList(@Nullable OrderedRealmCollection<Note> data) {
        if (data.size() == 0){

        } else {
            adapter = new MyNoteRecyclerViewAdapter(data, true, mListener);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryLight);
        swipeRefreshLayout.setOnRefreshListener(this::onSwipeList);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new MyNoteRecyclerViewAdapter(presenter.getItems(), true, mListener);
        recyclerView.setAdapter(adapter);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNoteItemLick(Note item);
        void onNoteFragmentInteractio(String name);
    }
}
