package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.Note;
import com.franq.dairy.View.DairyNoteFragment;

public interface DairyPresenter {

    void attachFragment(DairyNoteFragment fragment);
    void detachFragment();
    void openDB();
    void closeDB();
    void deleteNote(Note note);
}
