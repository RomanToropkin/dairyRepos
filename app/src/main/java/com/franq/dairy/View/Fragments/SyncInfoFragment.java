package com.franq.dairy.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.franq.dairy.Presenter.PSync.SyncPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.SyncContractView;

/**
 * @see CreatingFragment
 */
public class SyncInfoFragment extends Fragment implements SyncContractView {

    private onSynchFragmentInteractionListener mListener;
    private TextView loginTextView, secStatusTextView;
    private ImageView statusImage;
    private Button logoutButton;
    private SwipeRefreshLayout refreshLayout;
    private SyncPresenterImpl presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null){
            mListener.onSyncInteract("Синхронизация");
        }

        View view = inflater.inflate(R.layout.fragment_sync_info, container, false);
        initComponents(view);
        return view;
    }


    @Override
    public void toLoginFragment() {
        if ( mListener != null ) {
            mListener.onLogoutInteract( );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onSynchFragmentInteractionListener) {
            mListener = (onSynchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onSynchFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.checkAuthorizatiton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshList() {
        presenter.checkAuthorizatiton();
    }

    @Override
    public void onLogoutButtonClick(View view) {
        presenter.clearAuthorizationData();
        if (mListener != null) {
            mListener.onLogoutInteract();
        }
    }

    @Override
    public void changeStatus(boolean boolStatus) {
        if (boolStatus) {
            statusImage.setBackgroundResource(R.drawable.image_ok);
            loginTextView.setVisibility(View.VISIBLE);
            loginTextView.setText(presenter.getLogin());
            secStatusTextView.setText("Вы вошли как");
        } else {
            loginTextView.setVisibility(View.INVISIBLE);
            secStatusTextView.setText("Ошибка синхронизации! Повторите попытку позже");
            statusImage.setBackgroundResource(R.drawable.image_fail);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetachView();
    }

    @Override
    public void initComponents(View view) {

        secStatusTextView = view.findViewById(R.id.secStatusSyncTextView);
        loginTextView = view.findViewById(R.id.accountNameSynInfoTextView);
        statusImage = view.findViewById(R.id.syncStatusImage);
        logoutButton = view.findViewById(R.id.changeAccountButton);
        logoutButton.setOnClickListener(this::onLogoutButtonClick);
        refreshLayout = view.findViewById(R.id.syncRefresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryLight);
        refreshLayout.setOnRefreshListener(this::refreshList);
        presenter = new SyncPresenterImpl();
        presenter.onAttachView(this);
    }


    public interface onSynchFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSyncInteract(String name);
        void onLogoutInteract();
    }
}
