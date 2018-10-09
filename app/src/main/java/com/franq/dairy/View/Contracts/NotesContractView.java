package com.franq.dairy.View.Contracts;

import android.support.annotation.Nullable;

import com.franq.dairy.Model.DataBase.Note;

import io.realm.OrderedRealmCollection;

public interface NotesContractView extends BaseContractView {

    void onSwipeList();
    void refreshList(@Nullable OrderedRealmCollection<Note> notes);
}
