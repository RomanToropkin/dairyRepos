package com.franq.dairy.View.Contracts;

import android.view.View;

import com.franq.dairy.Model.JsonModels.Note;

/**
 * Интерфейс, описывающий поведение отображения выбранной записи
 */
public interface DairyContractView extends BaseContractView {

    void deleteButtonClick(View view);
    void setNote(Note note);
}
