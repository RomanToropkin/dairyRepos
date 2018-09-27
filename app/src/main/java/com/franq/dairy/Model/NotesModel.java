package com.franq.dairy.Model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class NotesModel {

    private Realm realm;
    private Context context;

    public NotesModel(Context context){
        this.context = context;
    }

    public void init(){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realm.notes")
                .build();
        realm = Realm.getInstance(config);
    }

    public void addNote(String title, String description, String imgUMI){
        SimpleDateFormat patternDate = new SimpleDateFormat("dd.M.yyyy' 'HH:mm");
        String date = patternDate.format(new Date());
        realm.beginTransaction();
        Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
        note.setTitle(title);
        note.setDescription(description);
        note.setDate(date);
        note.setImageUMI(imgUMI);
        realm.commitTransaction();
    }

    public void deleteNote(Note note){
        RealmResults<Note> results = realm.where(Note.class).equalTo("id", note.getId()).findAll();
        realm.executeTransaction(realm -> results.deleteAllFromRealm());
    }

    public void addNote(String title, String description){

        addNote(title, description,  null);
    }

    public List<Note> getAllObjects(){
        return realm.where(Note.class).findAll();
    }

    public RealmResults<Note> getItems(){
        SimpleDateFormat format = new SimpleDateFormat("dd.M.yyyy");
        String date = format.format(new Date());
        RealmResults<Note> res = realm.where(Note.class)
                .beginsWith("date", date)
                .findAll();
        return res;
    }

    public void close(){
        realm.close();
    }

    public RealmResults<Note> getNotesByDate(int day, int month, int year){
        String date = day+"."+month+"."+year;
        RealmResults<Note> res = realm.where(Note.class)
                .beginsWith("date", date)
                .findAll();
        return res;
    }

}
