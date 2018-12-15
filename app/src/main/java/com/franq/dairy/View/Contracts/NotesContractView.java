package com.franq.dairy.View.Contracts;

import com.franq.dairy.Model.JsonModels.Note;

import java.util.List;

/**
 * Интерфейс, описывающий поведение отображения списка записей
 */
public interface NotesContractView extends BaseContractView {

    void onSwipeList();

    void refreshList(List <Note> notes);

    void showLoading();

    void hideLoading();
}
