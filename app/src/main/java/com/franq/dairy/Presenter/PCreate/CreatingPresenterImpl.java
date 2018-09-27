package com.franq.dairy.Presenter.PCreate;

import com.franq.dairy.Model.NotesModel;
import com.franq.dairy.R;
import com.franq.dairy.View.CreatingFragment;
import com.franq.dairy.View.NoteFragment;

public class CreatingPresenterImpl implements CreatingPresenter {

    CreatingFragment fragment;
    NotesModel model;

    @Override
    public void attachFragment(CreatingFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void detachFragment() {
        fragment = null;
    }

    @Override
    public void openDB() {
        this.model = new NotesModel(fragment.getContext());
        model.init();
    }

    @Override
    public void closeDB() {
        model.close();
    }

    @Override
    public void createNote(String title, String description) {
        model.addNote(title, description);
    }
}
