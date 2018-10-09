package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.DataBase.Note;

public interface DairyPresenter {

    void openDB();
    void closeDB();
    void deleteNote(Note note);
}
