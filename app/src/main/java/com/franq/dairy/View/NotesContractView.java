package com.franq.dairy.View;

import android.support.annotation.Nullable;
import android.view.View;

import com.franq.dairy.Model.Note;

import java.util.List;

import io.realm.OrderedRealmCollection;

public interface NotesContractView {

    void onSwipeList();
    void initSwipeRefreshLayout(View view);
    void initRecycler(View view);
    void refreshList(@Nullable OrderedRealmCollection<Note> notes);
}
