package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.DairyNoteFragment;

/**
 * Представитель, который удаляет запись из репозитория
 **/
public class DairyPresenterImpl extends BasePresenter<DairyNoteFragment> implements DairyPresenter {
    /**Репозиторий
     * @see NotesModel*/
    private NotesModel model;

    /**Связь модели с представителем и инициализация БД*/
    @Override
    public void openDB() {
        model = new NotesModel(view.getContext());
        model.init();
    }

    /**Удаление записи из БД
     * @param note запись, которую нужно удалить*/
    @Override
    public void deleteNote(Note note) {
        model.deleteNote(note);
    }

    /**Закрытие БД*/
    @Override
    public void closeDB() {
        model.close();
    }
}
