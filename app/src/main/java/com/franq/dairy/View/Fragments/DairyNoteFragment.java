package com.franq.dairy.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Presenter.PDairy.DairyPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.DairyContractView;


/**
 * @see CreatingFragment
 */
public class DairyNoteFragment extends Fragment implements DairyContractView {

    private TextView textView;
    private Button deleteButton;
    private DairyPresenterImpl presenter;
    private Note note;
    private RecyclerView recyclerView;

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    private onDairyNoteFragmentInteraction mListener;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetachView();
    }

    @Override
    public void initComponents(View view) {
        presenter = new DairyPresenterImpl( );
        presenter.onAttachView( this );

        textView = view.findViewById(R.id.textDairyView);
        textView.setText(note.getDescription());
        deleteButton = view.findViewById(R.id.deleteDairyButton);
        deleteButton.setOnClickListener(this::deleteButtonClick);
        recyclerView = view.findViewById( R.id.rvDairyImages );
        presenter.addAdapter( recyclerView, note.getId( ) );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dairy_note, container, false);
        initComponents(view);
        if ( mListener != null ) {
            mListener.onDairyFragmentInteract( note.getTitle( ) );
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onDairyNoteFragmentInteraction) {
            mListener = (onDairyNoteFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onSynchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void deleteButtonClick(View view) {
        presenter.deleteNote(note);
        if (mListener != null){
            mListener.onDairyDeleteFragment();
        }
    }

    public interface onDairyNoteFragmentInteraction {

        void onDairyFragmentInteract(String name);

        void onDairyDeleteFragment();
    }
}
