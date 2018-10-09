package com.franq.dairy.Presenter.PNote;

import com.franq.dairy.Model.DataBase.Note;

import java.util.List;

import io.realm.RealmResults;

public interface NotePresenter {

    void openDB();
    List<Note> getAllNotes();
    void addNote();
    RealmResults<Note> getItems();
    void closeDB();

}
