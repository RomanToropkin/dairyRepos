package com.franq.dairy.Presenter.PCreate;

import android.annotation.SuppressLint;
import android.util.Log;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.NoteDate;
import com.franq.dairy.View.Fragments.CreatingFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Представитель создания записи, который получает данные от фрагмента и передает их репозиторию
 */
public class CreatingPresenterImpl extends BasePresenter <CreatingFragment> implements CreatingPresenter {
    /**
     * Репозиторий
     *
     * @see NotesModel
     */
    private NotesModel model;

    @Override
    public void onDetachView() {
        super.onDetachView( );
    }

    @Override
    public void onAttachView(CreatingFragment view) {
        super.onAttachView( view );
        model = NotesModel.getInstance( view.getContext( ) );
    }

    /**
     * Добавление новой записи в БД
     *
     * @param title       заголовок
     * @param description контент
     */
    @SuppressLint("CheckResult")
    @Override
    public void createNote(String title, String description, ArrayList <String> imagesUri) {
        String date = NoteDate.getFullDate( );
        String UID = UUID.randomUUID( ).toString( );
        Note note = model.addNote( UID, date, title, description, imagesUri );
        Server server = Server.getInstance( view.getContext( ) );
        server.addNote( note )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .map( Response::body )
                .subscribe( result -> {
                            if ( result != null ) {
                                Log.d( TAG, "Result is " + result.getResult( ) );
                            } else {
                                Log.d( TAG, "Result is null!" );
                            }
                        },
                        error -> Log.d( TAG, "Error : " + error.getMessage( ) ) );
        if ( imagesUri != null ) {
            ArrayList <File> images = new ArrayList <>( );
            for (String uri : imagesUri) {
                File file = new File( uri );
                images.add( file );
            }
            server.sendImages( UID, images )
                    .subscribeOn( Schedulers.io( ) )
                    .observeOn( AndroidSchedulers.mainThread( ) )
                    .map( Response::body )
                    .subscribe( result -> {
                        if ( result != null ) {
                            Log.d( TAG, "Result is " + result.getResult( ) );
                        } else {
                            Log.d( TAG, "Result is null!" );
                        }
                    }, error -> Log.d( TAG, "Error : " + error.getMessage( ) ) );
        }
    }


}
