package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.DairyNoteFragment;

public class DairyPresenterImpl extends BasePresenter<DairyNoteFragment> implements DairyPresenter {

    private NotesModel model;

    @Override
    public void openDB() {
        model = new NotesModel(view.getContext());
        model.init();
    }

    @Override
    public void deleteNote(Note note) {
        model.deleteNote(note);
    }

    @Override
    public void closeDB() {
        model.close();
    }
}
