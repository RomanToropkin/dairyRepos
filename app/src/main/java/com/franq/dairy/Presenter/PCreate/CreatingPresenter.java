package com.franq.dairy.Presenter.PCreate;

public interface CreatingPresenter {

    void openDB();
    void closeDB();
    void createNote(String title, String description);

}
