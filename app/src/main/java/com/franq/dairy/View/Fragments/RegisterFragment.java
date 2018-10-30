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
import android.widget.TextView;

import com.franq.dairy.Presenter.PRegister.RegisterPresenterImp;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.RegisterContractView;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see CreatingFragment
 */
public class RegisterFragment extends Fragment implements RegisterContractView {


    private onRegisterFragmentInteraction mListener;

    private TextView loginHint, passHint;
    private EditText loginEdit, passEdit, secPassEdit;
    private Button regButton;
    private RegisterPresenterImp presenter;
    private ProgressBar progressBar;
    private boolean isClick = false;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

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
        progressBar = view.findViewById(R.id.registerRefresh);
        loginHint = view.findViewById(R.id.loginHintTextView);
        passHint = view.findViewById(R.id.passHintTextView);

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
        if (!isClick()) {
            String login = loginEdit.getText().toString();
            String pass = passEdit.getText().toString();
            String secPass = secPassEdit.getText().toString();
            Pattern loginPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{4,20}$");
            Pattern passPattern = Pattern.compile("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])((?=.*[A-Z])|(?=.*[a-z])).*$");
            Matcher passMathcer = passPattern.matcher(pass);
            Matcher loginMatcher = loginPattern.matcher(login);

            boolean flag = true;
            if (!loginMatcher.matches()) {
                loginHint.setText("Логин должен содержать символы латинского алфавита." +
                        "Длина логина от 5 до 20 символов");
                flag = false;
            } else {
                loginHint.setText("");
            }
            if (!passMathcer.matches()) {
                passHint.setText("Пароль должен содержать строчные или прописные буквы латинского алфавита, а также цифры или спецсимволы." +
                        "Длина пароль от 8 символов");
                flag = false;
            } else {
                passHint.setText("");
            }
            if (!pass.equals(secPass)) {
                showError("Пароли не совпадают");
                flag = false;
            }
            if (flag)
                presenter.registerUser(login, new String(Hex.encodeHex(DigestUtils.md5(pass))));
        }
    }

    @Override
    public void showError(String text) {
        Snackbar snackbar = Snackbar.make(this.getView(), text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.show();
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
