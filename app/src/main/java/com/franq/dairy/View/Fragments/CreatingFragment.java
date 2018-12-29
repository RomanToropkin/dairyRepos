package com.franq.dairy.View.Fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.franq.dairy.Presenter.PCreate.CreatingPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Adapters.CreateImageRecyclerViewAdapter;
import com.franq.dairy.View.Contracts.CreatingContractView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Все фрагменты имеют схожий функционал. Они являются посредниками между элементами отображения и их событиями.
 * Все они связываются с главной активностью.
 * Все они реализуют механизм обратного вызова и передают методы главной активности
 */

public class CreatingFragment extends Fragment implements CreatingContractView {

    private onCreatingFragmentInteractionListener mListener;
    private EditText tittleEdit, textEdit;
    private static final int SELECT_PICTURE = 22;
    private CreatingPresenterImpl presenter;
    private final String TAG = getClass( ).getSimpleName( );
    private Button addButton, imageButton;
    private ArrayList <String> imageUris;
    private RecyclerView recyclerView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetachView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initComponents(View view) {
        tittleEdit = view.findViewById(R.id.titteEdit);
        textEdit = view.findViewById(R.id.textEdit);
        addButton = view.findViewById(R.id.addNoteButton);
        addButton.setOnClickListener( this::addNoteButtonClick );
        imageButton = view.findViewById( R.id.addImagesButton );
        imageButton.setOnClickListener( this::sendImagesButtonClick );
        recyclerView = view.findViewById( R.id.rvAddImage );
        presenter = new CreatingPresenterImpl();
        presenter.onAttachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onCreatingFragmentInteract("Создать запись");
        }
        View view = inflater.inflate(R.layout.fragment_creating, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCreatingFragmentInteractionListener) {
            mListener = (onCreatingFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onCreatingFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean checkTitle(String title) {
        return !title.equals("");
    }

    @Override
    public boolean checkText(String text) {
        return !text.equals("");
    }

    @Override
    public void addNoteButtonClick(View view) {
        Context focus = getContext();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) focus.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String title = tittleEdit.getText().toString();
        String description = textEdit.getText().toString();
        if (checkTitle(title) & checkTitle(description)){
            presenter.createNote( title, description, imageUris );
            if (mListener != null){
                mListener.onCreatingNote();
            }
        } else {
            Snackbar snackbar = Snackbar.make(this.getView(), "Заполните все поля ввода!", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundResource(R.color.colorAccent);
            snackbar.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == RESULT_OK
                && requestCode == SELECT_PICTURE
                && data != null ) {
            imageUris = new ArrayList <>( );
            if ( data.getData( ) != null ) {
                Uri uri = data.getData( );
                imageUris.add( getRealPathFromUri( uri ) );
            } else {
                ClipData clipData = data.getClipData( );
                for (int i = 0; i < clipData.getItemCount( ); i++) {
                    Uri uri = clipData.getItemAt( i ).getUri( );
                    imageUris.add( getRealPathFromUri( uri ) );
                }
            }
            CreateImageRecyclerViewAdapter adapter = new CreateImageRecyclerViewAdapter( imageUris );
            recyclerView.setLayoutManager( new LinearLayoutManager( getContext( ) ) );
            recyclerView.setAdapter( adapter );
        }
    }

    @Override
    public void sendImagesButtonClick(View view) {
        Intent intent = new Intent( );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
        startActivityForResult( Intent.createChooser( intent, "Select Picture" ), SELECT_PICTURE );
    }

    public interface onCreatingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCreatingFragmentInteract(String name);

        void onCreatingNote();
    }

    private String getRealPathFromUri(Uri contentUri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId( contentUri );

        // Split at colon, use second item in the array
        String id = wholeID.split( ":" )[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContext( ).getContentResolver( ).query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null );

        int columnIndex = cursor.getColumnIndex( column[0] );

        if ( cursor.moveToFirst( ) ) {
            filePath = cursor.getString( columnIndex );
        }
        cursor.close( );
        return filePath;
    }


}
