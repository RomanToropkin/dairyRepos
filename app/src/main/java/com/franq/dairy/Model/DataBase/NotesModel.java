package com.franq.dairy.Model.DataBase;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Класс - репозиторий базы данных записей. Архитектура приложения базируется на паттерне MVP (model-view-presenter)
 * Данный класс является моделью (бизнес-логикой) приложения.
 */
public class NotesModel {
    /**
     * Менеджер всех объектов в базе данных. Все операции с базой данных происходят с помощью этого объекта.
     */
    private Realm realm;
    /**Ссылка на контекст приложения*/
    private Context context;

    /**Конструктор - устанавливает ссылку на контекст приложения
     * @param context контекст приложения */
    public NotesModel(Context context){
        this.context = context;
    }

    /**Инициализирует хранилище данных для дальнейшей эксплуатации и производит конфигурацию настроек*/
    public void init(){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realm.notes")
                .build();
        realm = Realm.getInstance(config);
    }

    /**Добавляет новую запись в базу данных (БД)
     * @param title заголовок
     * @param description текстовый контент
     * @param imgUMI местоположение изображений */
    public void addNote(String title, String description, String imgUMI) {
        SimpleDateFormat patternDate = new SimpleDateFormat("d.M.yyyy' 'HH:mm");
        String date = patternDate.format(new Date());
        realm.beginTransaction();
        Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
        note.setTitle(title);
        note.setDescription(description);
        note.setDate(date);
        note.setImageUMI(imgUMI);
        realm.commitTransaction();
    }

    /**Удаляет запись из БД
     * @param note запись*/
    public void deleteNote(Note note){
        RealmResults<Note> results = realm.where(Note.class).equalTo("id", note.getId()).findAll();
        realm.executeTransaction(realm -> results.deleteAllFromRealm());
    }

    /**Добавляет запись без изображения
     * @see NotesModel#addNote(String, String, String) */
    public void addNote(String title, String description){
        addNote(title, description, null);
    }

    /**Возвращает список всех записей. Используется для тестов!*/
    public List<Note> getAllObjects(){
        return realm.where(Note.class).findAll();
    }

    /**
     * Возвращает список записей текущего дня
     *
     * @return список записей
     */
    public RealmList<Note> getItems() {
        SimpleDateFormat format = new SimpleDateFormat("d.M.yyyy");
        String date = format.format(new Date());
        RealmResults<Note> res = realm.where(Note.class)
                .beginsWith("date", date)
                .findAll();
        RealmList<Note> list = new RealmList<>();
        list.addAll(res.subList(0, res.size()));
        return list;
    }

    /**Закрывает хранилище. Завершение работы с БД*/
    public void close(){
        realm.close();
        realm = null;
    }

    /**
     * Возвращает список записей конкретной даты
     *
     * @param day   числовое значение дня
     * @param month числовое значение месяца
     * @param year  числое значение года
     */
    public RealmList<Note> getNotesByDate(int day, int month, int year){
        String date = day+"."+month+"."+year;
        RealmResults<Note> res = realm.where(Note.class)
                .beginsWith("date", date)
                .findAll();
        RealmList<Note> list = new RealmList<>();
        list.addAll(res.subList(0, res.size()));
        return list;
    }

}
