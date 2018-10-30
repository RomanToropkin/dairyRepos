package com.franq.dairy.Presenter.PNote;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.NoteFragment;

import java.util.List;

import io.realm.RealmList;

/**
 * Представитель, взаимодействующий с фрагментов отображения записей
 */
public class NotePresenterImpl extends BasePresenter<NoteFragment> implements NotePresenter {
    /**Репозиторий*/
    private NotesModel model;

    /**Связь модели с представителем и инициализация БД*/
    @Override
    public void openDB() {
        this.model = new NotesModel(view.getContext());
        model.init();
    }

    /**Возвращает список всех записей в БД. ТЕСТОВЫЙ МЕТОД!
     *@return список записей */
    @Override
    public List<Note> getAllNotes() {
        return model.getAllObjects();
    }

    /**Возвращает список записей текущего дня
     *@return список записей*/
    @Override
    public RealmList<Note> getList() {
        return model.getItems();
    }

    /**Закрытие БД*/
    @Override
    public void closeDB() {
        model.close();
    }

}
