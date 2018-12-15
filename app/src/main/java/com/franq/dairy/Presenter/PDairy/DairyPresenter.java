package com.franq.dairy.Presenter.PDairy;

import android.support.v7.widget.RecyclerView;

import com.franq.dairy.Model.JsonModels.Note;

/**
 * Интерфейс, описывающий представителя отображения записи
 */
public interface DairyPresenter {

    void deleteNote(Note note);

    void addAdapter(RecyclerView recyclerView, String noteId);
}
