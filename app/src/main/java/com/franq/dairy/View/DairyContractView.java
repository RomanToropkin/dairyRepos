package com.franq.dairy.View;

import android.view.View;

import com.franq.dairy.Model.Note;

public interface DairyContractView {

    void deleteButtonClick(View view);
    void setNote(Note note);
}
