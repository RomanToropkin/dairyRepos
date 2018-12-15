package com.franq.dairy.Presenter.PNote;

import android.annotation.SuppressLint;
import android.util.Log;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Model.JsonModels.MReq;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.NoteDate;
import com.franq.dairy.View.Fragments.NoteFragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Представитель, взаимодействующий с фрагментов отображения записей
 */
public class NotePresenterImpl extends BasePresenter<NoteFragment> implements NotePresenter {

    @Override
    public void getSynchronizeNotes() {
        view.showLoading( );
        String date = NoteDate.getPickedDate( );
        disposables.add(
                model.getNotesByDate( date )
                        .observeOn( AndroidSchedulers.mainThread( ) )
                        .subscribeWith( new DisposableSubscriber <List <Note>>( ) {
                            @SuppressLint("CheckResult")
                            @Override
                            public void onNext(List <Note> notes) {
                                MReq req = new MReq( );
                                req.setDate( date );
                                req.setNotes( notes );
                                view.setNoteList( notes );
                                Server.getInstance( view.getContext( ) )
                                        .getSyncNotes( req )
                                        .subscribeOn( Schedulers.io( ) )
                                        .observeOn( AndroidSchedulers.mainThread( ) )
                                        .map( Response::body )
                                        .subscribe( result -> {
                                            model.updateInfo( result );
                                        }, error -> {
                                            Log.d( TAG, "Error :" + error.getMessage( ) );
                                            view.refreshList( model.rgetNotesByDate( date ) );
                                            view.hideLoading( );
                                        }, () -> {
                                            view.refreshList( model.rgetNotesByDate( date ) );
                                            view.hideLoading( );
                                        } );
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.d( TAG, "Error :" + t.getMessage( ) );
                                view.hideLoading( );
                            }

                            @Override
                            public void onComplete() {
                                view.hideLoading( );
                            }
                        } )
        );
    }

    /**Репозиторий*/
    private NotesModel model;

    @Override
    public void onDetachView() {
        super.onDetachView( );
    }

    @Override
    public void onAttachView(NoteFragment view) {
        super.onAttachView( view );
        model = NotesModel.getInstance( view.getContext( ) );
    }

    @SuppressLint("CheckResult")
    public void doSynchronizeNotes() {
        String date = NoteDate.getPickedDate( );
        List <Note> noteList = model.rgetNotesByDate( date );
        MReq req = new MReq( );
        req.setDate( date );
        req.setNotes( noteList );
        Server.getInstance( view.getContext( ) )
                .getSyncNotes( req )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .map( Response::body )
                .subscribe( result -> {
                    model.updateInfo( result );
                }, error -> {
                    Log.d( TAG, "Error :" + error.getMessage( ) );
                } );
    }

}
