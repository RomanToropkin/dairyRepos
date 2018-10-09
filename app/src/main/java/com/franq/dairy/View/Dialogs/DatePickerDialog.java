package com.franq.dairy.View.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    private onDatePickListener listener;
    private Dialog picker;
    private String date;

    @SuppressLint("ValidFragment")
    public DatePickerDialog(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String [] dates = date.split("\\.");

        picker = new android.app.DatePickerDialog(getActivity(), this, Integer.parseInt(dates[2]), Integer.parseInt(dates[1])-1, Integer.parseInt(dates[0]));
        picker.setTitle("Выберите дату");

        return picker;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText("Готово");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onDatePickListener){
            listener = (onDatePickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDatePickListener");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        listener.onDatePick(year, month+1, day);
    }

    public interface onDatePickListener{
        void onDatePick(int year, int month, int day);
    }

}


