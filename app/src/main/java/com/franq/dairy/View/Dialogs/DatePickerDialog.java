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

/**
 * Реализация собственного диалогово окна с выбором даты из календаря.
 */
@SuppressLint("ValidFragment")
public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    /**Слушитель нажатия на дату в календаре */
    private onDatePickListener listener;
    /**Диалоговое окно*/
    private Dialog picker;
    /**Дата в строковом виде*/
    private String date;

    /**Конструктор, в которой передаётся дата
     * @param date дата в строковом виде*/
    @SuppressLint("ValidFragment")
    public DatePickerDialog(String date) {
        this.date = date;
    }

    /**Вызывается при создании диалогового окна*/
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String [] dates = date.split("\\.");

        picker = new android.app.DatePickerDialog(getActivity(), this, Integer.parseInt(dates[2]), Integer.parseInt(dates[1])-1, Integer.parseInt(dates[0]));
        picker.setTitle("Выберите дату");

        return picker;
    }

    /**Вызывается при запуске диалогового окна*/
    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText("Готово");
    }

    /**Вызывается при отвязки диалогвоого окна с активностью*/
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**Вызывается при связки диалогвоого окна с активностью*/
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

    /**Вызывается при выборе даты*/
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        listener.onDatePick(year, month+1, day);
    }

    /**Слушатель календаря*/
    public interface onDatePickListener {
        /**Выбрали дату
         * @param year числовое значение года
         * @param month числовое значение месяца
         * @param day числовое значение дня*/
        void onDatePick(int year, int month, int day);
    }

}


