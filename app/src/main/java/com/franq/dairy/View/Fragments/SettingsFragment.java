package com.franq.dairy.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.SettingsContractView;


/**
 * @see CreatingFragment
 */
public class SettingsFragment extends Fragment implements SettingsContractView {
    private OnFragmentInteractionListener mListener;
    private EditText ipTextEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onSettingsFragmentInteract("Настройки");
        }

        View view = inflater.inflate( R.layout.fragment_settings, container, false );
        initComponents( view );

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onSynchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onButtonClick(View view) {
        String url = "http://" + ipTextEdit.getText( ).toString( ) + ":8080/";
        Server.baseURL = url;
        Log.d( "IP", "IP стал : " + Server.baseURL );
        Snackbar snackbar = Snackbar.make( view, "Адресс успешно изменен на : " + Server.baseURL, Snackbar.LENGTH_SHORT );
        snackbar.getView( ).setBackgroundResource( R.color.colorAccent );
        snackbar.show( );
    }

    @Override
    public void initComponents(View view) {
        ipTextEdit = view.findViewById( R.id.editIP );
        Button button = view.findViewById( R.id.btnIP );
        button.setOnClickListener( this::onButtonClick );
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSettingsFragmentInteract(String name);
    }
}
