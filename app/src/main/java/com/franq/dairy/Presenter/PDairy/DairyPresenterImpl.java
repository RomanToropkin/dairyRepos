package com.franq.dairy.Presenter.PDairy;

import com.franq.dairy.Model.Note;
import com.franq.dairy.Model.NotesModel;
import com.franq.dairy.R;
import com.franq.dairy.View.DairyNoteFragment;
import com.franq.dairy.View.NoteFragment;

public class DairyPresenterImpl implements DairyPresenter {

    private DairyNoteFragment fragment;
    private NotesModel model;

    @Override
    public void attachFragment(DairyNoteFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void detachFragment() {
        fragment = null;
    }

    @Override
    public void openDB() {
        model = new NotesModel(fragment.getContext());
        model.init();
    }

    @Override
    public void deleteNote(Note note) {
        model.deleteNote(note);
        fragment.getFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    @Override
    public void closeDB() {
        model.close();
    }
}
