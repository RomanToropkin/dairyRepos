package com.franq.dairy.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.franq.dairy.Presenter.PCreate.CreatingPresenterImpl;
import com.franq.dairy.Presenter.PNote.NotePresenterImp;
import com.franq.dairy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onCreatingFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatingFragment extends Fragment implements CreatingContractView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private onCreatingFragmentInteractionListener mListener;
    private EditText tittleEdit, textEdit;
    private Button addButton;
    private CreatingPresenterImpl presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachFragment(this);
        presenter.openDB();
    }

    public CreatingFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachFragment();
        presenter.closeDB();
    }

    public static CreatingFragment newInstance(String param1, String param2) {
        CreatingFragment fragment = new CreatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onCreatingFragmentInteraction("Создать запись");
        }
        View view = inflater.inflate(R.layout.fragment_creating, container, false);
        tittleEdit = (EditText) view.findViewById(R.id.titteEdit);
        textEdit = (EditText) view.findViewById(R.id.textEdit);
        addButton = (Button) view.findViewById(R.id.addNoteButton);
        addButton.setOnClickListener(this::buttonClick);
        presenter = new CreatingPresenterImpl();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCreatingFragmentInteractionListener) {
            mListener = (onCreatingFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onCreatingFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean checkTitle(String title) {
        if (title.equals(""))
            return false;
        else
            return true;
    }

    @Override
    public boolean checkText(String text) {
        if (text.equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void buttonClick(View view) {
        if (tittleEdit.isFocused() | textEdit.isFocused()) {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        String title = tittleEdit.getText().toString();
        String description = textEdit.getText().toString();
        if (checkTitle(title) & checkTitle(description)){
            presenter.createNote(title, description);
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main_layout, new NoteFragment())
                    .commit();
            if (mListener != null){
                mListener.onCreatingNote();
            }
        } else {
            Snackbar.make(view, "Заполните все поля ввода!", Snackbar.LENGTH_LONG).show();
        }
    }

    public interface onCreatingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCreatingFragmentInteraction(String name);
        void onCreatingNote();
    }
}
