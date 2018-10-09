package com.franq.dairy.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.franq.dairy.Presenter.PRegister.RegisterPresenterImp;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.RegisterContractView;

public class RegisterFragment extends Fragment implements RegisterContractView {


    private onRegisterFragmentInteraction mListener;

    private EditText loginEdit, passEdit, secPassEdit;
    private Button regButton;
    private ProgressBar progressBar;
    private RegisterPresenterImp presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mListener != null)
            mListener.onRegisterFragmentInteract("Регистрация");

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initComponents(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onRegisterFragmentInteraction) {
            mListener = (onRegisterFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onSynchFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void initComponents(View view) {
        loginEdit = view.findViewById(R.id.loginRegEditTExt);
        passEdit = view.findViewById(R.id.passRegEditText);
        secPassEdit = view.findViewById(R.id.secondPassRegEditText);
        regButton = view.findViewById(R.id.registerButton);
        progressBar = view.findViewById(R.id.regProgressBar);

        regButton.setOnClickListener(this::onRegisterButtonClick);

        presenter = new RegisterPresenterImp();
        presenter.onAttachView(this);
    }

    @Override
    public void refreshFragment() {
        if (mListener != null) {
            mListener.onRegisterRefreshInteract();
        }
    }

    @Override
    public void onRegisterButtonClick(View view) {
        String login = loginEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String secPass = secPassEdit.getText().toString();
        if (pass.equals(secPass)) {
            presenter.registerUser(login, pass);
        } else {
            showError("Пароли не совпадают!");
        }
    }

    @Override
    public void showError(String text) {
        Snackbar.make(regButton, text, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface onRegisterFragmentInteraction {

        void onRegisterFragmentInteract(String name);

        void onRegisterRefreshInteract();
    }
}
