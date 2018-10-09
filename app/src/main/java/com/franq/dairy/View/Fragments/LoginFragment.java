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

import com.franq.dairy.Presenter.PLogin.LoginPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.LoginContractView;


public class LoginFragment extends Fragment implements LoginContractView {

    private OnFragmentInteractionListener mListener;
    private EditText loginEdit, passEdit;
    private Button regButton, loginButton;
    private LoginPresenterImpl presenter;
    private View activityView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteract("Авторизация");
        }

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(view);
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
    public void onButtonLoginClick(View view) {
        String login = loginEdit.getText().toString();
        String pass = passEdit.getText().toString();

        if (login.equals("")) {
            showError("Введите логин");
        } else if (pass.equals("")) {
            showError("Введите пароль");
        } else {
            presenter.authenticateUser(login, pass);
        }
    }

    @Override
    public void onRegisterButtonClick(View view) {
        if (mListener != null) {
            mListener.onChooseRegisterFragment();
        }
    }

    @Override
    public void initComponents(View view) {
        loginButton = view.findViewById(R.id.loginButton);
        regButton = view.findViewById(R.id.login_registerButton);
        loginEdit = view.findViewById(R.id.loginEditText);
        passEdit = view.findViewById(R.id.passEditText);
        loginButton.setOnClickListener(this::onButtonLoginClick);
        regButton.setOnClickListener(this::onRegisterButtonClick);
        activityView = getActivity().findViewById(R.id.content_main_layout);
        presenter = new LoginPresenterImpl();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onDetachView();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onAttachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showError(String errorText) {
        Snackbar snackbar = Snackbar.make(activityView, errorText, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.show();
    }

    @Override
    public void refreshFragment() {
        if (mListener != null)
            mListener.onLoginInteract();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteract(String name);

        void onChooseRegisterFragment();

        void onLoginInteract();
    }
}
