package com.franq.dairy.View.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.R;
import com.franq.dairy.View.Fragments.NoteFragment.OnListFragmentInteractionListener;

import io.realm.RealmList;

/**
 * Адаптер, который связывает фрагмент записи и источник данных этих записей
 */
public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    /**Слушатель, который реализует механизм обратного вызова с фрагментом*/
    private OnListFragmentInteractionListener mListener;
    /**
     * Список записей
     */
    private RealmList<Note> items;

    /**
     * Конструктор - добавляет данные и связывает слушатель
     *
     * @param items     список записей
     * @param mListener слушатель
     */
    public MyNoteRecyclerViewAdapter(RealmList<Note> items, OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
        this.items = items;
    }

    /**Паттерн ViewHolder служит для оптимизации загрузки и оторажения списка.
     * Его реализация позволяет избежать многократного вызова метода  findViewById()*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    /**Связь холдера с записью*/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        holder.mTimeView.setText(parseDateString(items.get(position).getDate()));
        holder.mContentView.setText(items.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onNoteItemLick(holder.mItem);
                }
            }
        });
    }

    /**
     * Обновление данных в списке
     *
     * @param data новый список
     */
    public void updateData(RealmList<Note> data) {
        items = data;
        notifyDataSetChanged();
    }

    /**Возвращает кол-во записей
     *@return кол-во записей*/
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Выделение времени из полной даты
     *
     * @param s полная дата
     */
    private String parseDateString(String s) {
        return s.substring(s.indexOf(' '));
    }

    /**Класс ViewHolder, реализующий паттерн*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Общее отображение
         */
        public final View mView;
        /**Время создания записи*/
        public final TextView mTimeView;
        /**Заголовок записи*/
        public final TextView mContentView;
        /**
         * Запись
         */
        public Note mItem;

        /**Конструктор - инициализация элементов
         * @param view общее отображение*/
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        /**Строково отображение холдера*/
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
