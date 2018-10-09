package com.franq.dairy.View.Contracts;

import android.view.View;

public interface CreatingContractView extends BaseContractView {

    void buttonClick(View view);
    boolean checkTitle(String title);
    boolean checkText(String text);
}
