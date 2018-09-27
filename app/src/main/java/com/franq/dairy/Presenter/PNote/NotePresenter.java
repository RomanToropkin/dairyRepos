package com.franq.dairy.Presenter.PNote;

import com.franq.dairy.Model.Note;
import com.franq.dairy.View.NoteFragment;

import java.util.List;

import io.realm.RealmResults;

public interface NotePresenter {

    void attachFragment(NoteFragment fragment);
    void detachFragment();
    void openDB();
    List<Note> getAllNotes();
    void addNote();
    RealmResults<Note> getItems();
    void closeDB();

}
