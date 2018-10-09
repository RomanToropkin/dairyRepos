package com.franq.dairy.Presenter.PNote;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.NoteFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.RealmResults;

public class NotePresenterImp extends BasePresenter<NoteFragment> implements NotePresenter {

    private NotesModel model;

    @Override
    public void openDB() {
        this.model = new NotesModel(view.getContext());
        model.init();
    }

    @Override
    public List<Note> getAllNotes() {
        return model.getAllObjects();
    }

    @Override
    public void addNote() {
        SimpleDateFormat format = new SimpleDateFormat("dd.M.yyyy' 'HH:mm");
        model.addNote(UUID.randomUUID().toString(),"123", format.format(new Date()));
    }

    @Override
    public RealmResults<Note> getItems() {
        return model.getItems();
    }

    @Override
    public void closeDB() {
        model.close();
    }

}
