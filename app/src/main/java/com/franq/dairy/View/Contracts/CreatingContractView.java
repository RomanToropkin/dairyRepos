package com.franq.dairy.View.Contracts;

import android.view.View;

/**
 * Интерфейс, описывающий поведение отображения создания записи
 */
public interface CreatingContractView extends BaseContractView {

    void buttonClick(View view);

    boolean checkTitle(String title);

    boolean checkText(String text);
}
