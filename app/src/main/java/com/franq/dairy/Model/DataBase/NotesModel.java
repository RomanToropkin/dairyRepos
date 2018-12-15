package com.franq.dairy.Model.DataBase;

import android.content.Context;

import com.franq.dairy.Model.JsonModels.ImageModel;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.Server.Server;

import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Класс - репозиторий базы данных записей. Архитектура приложения базируется на паттерне MVP (model-view-presenter)
 * Данный класс является моделью (бизнес-логикой) приложения.
 */
public class NotesModel {
    /**
     * Менеджер всех объектов в базе данных. Все операции с базой данных происходят с помощью этого объекта.
     */
    private static volatile NotesModel instance;

    private NotesModel(Context context) {
        Realm.init( context );
    }

    public static NotesModel getInstance(Context context) {
        NotesModel localInstance = instance;
        if ( localInstance == null ) {
            synchronized (NotesModel.class) {
                localInstance = instance;
                if ( localInstance == null ) {
                    instance = localInstance = new NotesModel( context );
                }
            }
        }
        return localInstance;
    }

    /**Добавляет новую запись в базу данных (БД)
     * @param title заголовок
     * @param description текстовый контент*/
    public Note addNote(String id, String date, String title, String description, List <String> imagesURI) {
        try (Realm realm = Realm.getDefaultInstance( )) {
            realm.beginTransaction( );
            Note note = realm.createObject( Note.class, id );
            note.setTitle( title );
            note.setDescription( description );
            note.setDate( date );
            realm.commitTransaction( );
            addImages( imagesURI, id );
            return realm.copyFromRealm( note );
        }
    }

    /**Удаляет запись из БД
     * @param note запись*/
    public void deleteNote(Note note){
        try (Realm realm = Realm.getDefaultInstance( )) {
            RealmResults <Note> results = realm.where( Note.class )
                    .equalTo( "id", note.getId( ) )
                    .findAll( );
            realm.executeTransaction( ex -> results.deleteAllFromRealm( ) );
        }
    }

    /**
     * Добавляет запись без изображения
     */
    public Note addNote(String id, String date, String title, String description) {
        return addNote( id, date, title, description, null );
    }

    public List <Note> rgetNotesByDate(String date) {
        try (Realm realm = Realm.getDefaultInstance( )) {
            return realm.copyFromRealm(
                    realm.where( Note.class )
                            .beginsWith( "date", date )
                            .findAll() );
        }
    }


    public Flowable <List <Note>> getNotesByDate(String date) {
        Realm realm = Realm.getDefaultInstance( );
        Flowable <List <Note>> flowable;
        flowable = realm
                .where( Note.class )
                .beginsWith( "date", date )
                .findAll( )
                .asFlowable( )
                .doOnComplete( realm::close )
                .filter( RealmResults::isLoaded )
                .map( realm::copyFromRealm );
        return flowable;
    }

    public void updateInfo(List <Note> notes) {
        try (Realm realm = Realm.getDefaultInstance( )) {
            realm.beginTransaction( );
            realm.insertOrUpdate( realm.copyToRealm( notes ) );
            realm.commitTransaction( );
        }
        for (Note note : notes)
            updateImages( note.getImageUMI( ), note.getId( ) );
    }

    private void addImages(List <String> imagesURI, String noteId) {
        try (Realm realm = Realm.getDefaultInstance( )) {
            for (String uri : imagesURI) {
                realm.beginTransaction( );
                ImageModel model = realm.createObject( ImageModel.class, UUID.randomUUID( ).toString( ) );
                model.setNoteId( noteId );
                model.setImageURI( uri );
                realm.commitTransaction( );
            }
        }
    }

    private void updateImages(List <String> imagesURI, String noteId) {
        try (Realm realm = Realm.getDefaultInstance( )) {
            RealmResults <ImageModel> realmResults = realm
                    .where( ImageModel.class )
                    .contains( "noteId", noteId )
                    .findAll( );
            realm.executeTransaction( ex -> realmResults.deleteAllFromRealm( ) );
        }

        try (Realm realm = Realm.getDefaultInstance( )) {
            for (String uri : imagesURI) {
                realm.beginTransaction( );
                ImageModel model = realm.createObject( ImageModel.class, UUID.randomUUID( ).toString( ) );
                model.setNoteId( noteId );
                model.setImageURI( Server.baseURL + uri );
                realm.insertOrUpdate( model );
                realm.commitTransaction( );
            }
        }
    }

    public Flowable <List <ImageModel>> getImagesURI(String noteId) {
        Realm realm = Realm.getDefaultInstance( );
        Flowable <List <ImageModel>> flowable;
        flowable = realm
                .where( ImageModel.class )
                .contains( "noteId", noteId )
                .findAll( )
                .asFlowable( )
                .doOnComplete( realm::close )
                .filter( RealmResults::isLoaded )
                .map( realm::copyFromRealm );
        return flowable;
    }

}
