package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.DataBase.Note;

/**
 * Интерфейс, описывающий представителя отображения записи
 */
public interface DairyPresenter {

    void openDB();
    void closeDB();
    void deleteNote(Note note);
}
