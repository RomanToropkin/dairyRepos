package com.franq.dairy.Presenter.PCreate;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.CreatingFragment;

public class CreatingPresenterImpl extends BasePresenter<CreatingFragment> implements CreatingPresenter {

    NotesModel model;

    @Override
    public void openDB() {
        this.model = new NotesModel(view.getContext());
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
