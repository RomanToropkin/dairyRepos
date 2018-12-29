package com.franq.dairy.Presenter.PDairy;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.franq.dairy.Model.local.NotesModel;
import com.franq.dairy.Model.JsonModels.ImageModel;
import com.franq.dairy.Model.JsonModels.MDeleteNote;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.remote.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Adapters.ImageRecyclerViewAdapter;
import com.franq.dairy.View.Dialogs.ImageDialog;
import com.franq.dairy.View.Fragments.DairyNoteFragment;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Представитель, который удаляет запись из репозитория
 **/
public class DairyPresenterImpl extends BasePresenter<DairyNoteFragment> implements DairyPresenter {
    /**Репозиторий
     * @see NotesModel*/
    private NotesModel model;

    @Override
    public void onDetachView() {
        super.onDetachView( );
    }

    @Override
    public void onAttachView(DairyNoteFragment view) {
        super.onAttachView( view );
        model = NotesModel.getInstance( view.getContext( ) );
    }

    /**Удаление записи из БД
     * @param note запись, которую нужно удалить*/
    @SuppressLint("CheckResult")
    @Override
    public void deleteNote(Note note) {
        String id = note.getId( );
        MDeleteNote mDeleteNote = new MDeleteNote( );
        mDeleteNote.setId(id);
        model.deleteNote(note);
        Server server = Server.getInstance( view.getContext( ) );
        server.deleteNote( mDeleteNote )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .map( Response::body )
                .subscribe(
                        result -> Log.d( TAG, "Result : " + result.getResult( ) ),
                        error -> Log.d( TAG, "Error : " + error.getMessage( ) ),
                        () -> view.refreshFragment());
//        server.downloadImages( id )
//                .subscribeOn( Schedulers.io() )
//                .observeOn( AndroidSchedulers.mainThread() )
//                .map( Response::body )
//                .subscribe( result ->{
//                            ZipInputStream zin = new ZipInputStream( result.byteStream() );
//                            ZipEntry zipEntry;
//                            while ((zipEntry = zin.getNextEntry()) != null){
//                                String name = zipEntry.getName();
//                            }
//                },
//                        error -> Log.d(TAG, "Error : "+error.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void addAdapter(RecyclerView recyclerView, String noteId) {
        model.getImagesURI( noteId )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .subscribe( result -> {
                    ArrayList <String> imagesURI = new ArrayList <>( );
                    for (ImageModel model : result) {
                        imagesURI.add( model.getImageURI( ) );
                    }
                    ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter( imagesURI, uri ->{
                        ImageDialog dialog = new ImageDialog();
                        dialog.setUri(uri );
                        dialog.show( view.getFragmentManager(), "ImageDialog" );
                    } );
                    recyclerView.setLayoutManager( new LinearLayoutManager( view.getContext( ) ) );
                    recyclerView.setAdapter( adapter );
                }, error -> Log.d( TAG, "Error : " + error.getMessage( ) ) );
    }

}
