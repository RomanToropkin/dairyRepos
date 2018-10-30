package com.franq.dairy.Presenter.PCreate;

/**
 * Интерфейс, описывающий представителя создания записи
 */
public interface CreatingPresenter {

    void openDB();

    void closeDB();

    void createNote(String title, String description);

}
