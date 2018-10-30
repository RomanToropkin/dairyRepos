package com.franq.dairy.Presenter.PCreate;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.CreatingFragment;

/**
 * Представитель создания записи, который получает данные от фрагмента и передает их репозиторию
 */
public class CreatingPresenterImpl extends BasePresenter<CreatingFragment> implements CreatingPresenter {
    /**
     * Репозиторий
     *
     * @see NotesModel
     */
    NotesModel model;

    /**Связь модели с представителем и инициализация БД*/
    @Override
    public void openDB() {
        this.model = new NotesModel(view.getContext());
        model.init();
    }

    /**Закрытие БД*/
    @Override
    public void closeDB() {
        model.close();
    }

    /**Добавление новой записи в БД
     * @param title заголовок
     * @param description контент*/
    @Override
    public void createNote(String title, String description) {
        model.addNote(title, description);
    }
}
