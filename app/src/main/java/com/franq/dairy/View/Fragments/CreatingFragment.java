package com.franq.dairy.View.Fragments;

import android.app.Activity;
import android.content.Context;
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
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.CreatingContractView;

public class CreatingFragment extends Fragment implements CreatingContractView {

    private onCreatingFragmentInteractionListener mListener;
    private EditText tittleEdit, textEdit;
    private Button addButton;
    private CreatingPresenterImpl presenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeDB();
        presenter.onDetachView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initComponents(View view) {
        tittleEdit = view.findViewById(R.id.titteEdit);
        textEdit = view.findViewById(R.id.textEdit);
        addButton = view.findViewById(R.id.addNoteButton);
        addButton.setOnClickListener(this::buttonClick);
        presenter = new CreatingPresenterImpl();
        presenter.onAttachView(this);
        presenter.openDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onCreatingFragmentInteract("Создать запись");
        }
        View view = inflater.inflate(R.layout.fragment_creating, container, false);
        initComponents(view);
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
        return !title.equals("");
    }

    @Override
    public boolean checkText(String text) {
        return !text.equals("");
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
            if (mListener != null){
                mListener.onCreatingNote();
            }
        } else {
            Snackbar.make(view, "Заполните все поля ввода!", Snackbar.LENGTH_LONG).show();
        }
    }

    public interface onCreatingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCreatingFragmentInteract(String name);
        void onCreatingNote();
    }
}
