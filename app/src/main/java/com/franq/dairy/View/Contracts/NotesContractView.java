package com.franq.dairy.View.Contracts;

import com.franq.dairy.Model.DataBase.Note;

import io.realm.RealmList;

/**
 * Интерфейс, описывающий поведение отображения списка записей
 */
public interface NotesContractView extends BaseContractView {

    void onSwipeList();

    void refreshList(RealmList<Note> notes);
}
