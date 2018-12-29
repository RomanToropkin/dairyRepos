package com.franq.dairy.Presenter.PNote;

import android.annotation.SuppressLint;
import android.util.Log;

import com.franq.dairy.Model.local.NotesModel;
import com.franq.dairy.Model.JsonModels.MReq;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.Model.remote.Server;
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
        String login = PreferencesData
                .getInstance( view.getContext() )
                .getLogin();
        disposables.add(
                model.getNotesByDate( login, date )
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
                                            if (result.size() != 0) {model.updateInfo(login, result );}
                                        }, error -> {
                                            Log.d( TAG, "Error :" + error.getMessage( ) );
                                            if (view != null) {
                                                view.refreshList( model.rgetNotesByDate( login, date ) );
                                                view.hideLoading( );
                                            }
                                        }, () -> {
                                            if (view != null) {
                                                view.refreshList( model.rgetNotesByDate( login, date ) );
                                                view.hideLoading( );
                                            }
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
    public void onAttachView(NoteFragment view) {
        super.onAttachView( view );
        model = NotesModel.getInstance( view.getContext( ) );
    }

}
