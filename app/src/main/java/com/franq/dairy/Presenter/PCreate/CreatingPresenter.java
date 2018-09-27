package com.franq.dairy.Presenter.PCreate;

import android.app.Activity;

import com.franq.dairy.View.CreatingFragment;

public interface CreatingPresenter {

    void attachFragment(CreatingFragment fragment);
    void detachFragment();
    void openDB();
    void closeDB();
    void createNote(String title, String description);

}
