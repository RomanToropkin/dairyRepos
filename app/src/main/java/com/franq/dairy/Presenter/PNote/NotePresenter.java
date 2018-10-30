package com.franq.dairy.Presenter.PNote;

import com.franq.dairy.Model.DataBase.Note;

import java.util.List;

import io.realm.RealmList;

/**
 * Интерфейс, описывающий представителя отображения записей
 */
public interface NotePresenter {

    void openDB();
    List<Note> getAllNotes();

    RealmList<Note> getList();
    void closeDB();

}
