package com.franq.dairy.Presenter.PMain;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.franq.dairy.Model.APIService;
import com.franq.dairy.Model.NotesModel;
import com.franq.dairy.R;
import com.franq.dairy.View.CreatingFragment;
import com.franq.dairy.View.DairyNoteFragment;
import com.franq.dairy.View.NoteFragment;
import com.franq.dairy.View.SettingsFragment;
import com.franq.dairy.View.SyncInfoFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenterImpl implements MainPresenter {

    private Activity activity;
    private NotesModel model;
    private FragmentManager fragmentManager;
    private static final String NOTE_STACK_NAME = "notes";
    private static final String CREATE_STACK_NAME = "create";
    private static final String SETTINGS_STACK_NAME = "settings";
    private static final String SYNC_STACK_NAME = "sync";
    private static final String MAIN_RRESENTER_TAG = "mainPresenter";
    private boolean isNoteFragment = false;
    private boolean isCreateFragment = false;
    private boolean isSyncFragment = false;
    private boolean isSettingsFragment = false;
    public static final String BASE_URL = "http://localhost:8080";

    @Override
    public void attachView(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void detachView() {
        activity = null;
    }

    @Override
    public void openDB() {
        model = new NotesModel(activity.getApplicationContext());
        model.init();
    }

    @Override
    public void openAddNoteFragment() {
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        if (!isCreateFragment & fragmentManager.getBackStackEntryCount() < 1){
            isNoteFragment = false;
            isSettingsFragment = false;
            isCreateFragment = true;
            isSyncFragment = false;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new CreatingFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            clearFirstFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new CreatingFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void openStartFragment() {
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        isNoteFragment = true;
        fragmentManager.beginTransaction()
                .add(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    @Override
    public void testServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Call <String> call = apiService.testLoad();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(MAIN_RRESENTER_TAG, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(MAIN_RRESENTER_TAG, t.getMessage());
            }
        });
    }

    @Override
    public void openDairyNoteFragment(DairyNoteFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getStackSize() {
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        return fragmentManager.getBackStackEntryCount();
    }

    @Override
    public void refreshStack() {
//        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+stackCount);
//        if (stackCount!=0)
//            stackCount--;
    }

    @Override
    public void onChangeDate(int day, int month, int year) {
        NoteFragment fragment = (NoteFragment) fragmentManager.findFragmentById(R.id.content_main_layout);
        fragment.refreshList(model.getNotesByDate(day, month, year));
    }

    @Override
    public void openNotesFragment() {
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() < 1 & !isNoteFragment){
            isNoteFragment = true;
            isSettingsFragment = false;
            isCreateFragment = false;
            isSyncFragment = false;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new NoteFragment())
                    .addToBackStack(null)
                    .commit();
        } else  if (!isNoteFragment){
            clearFirstFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new NoteFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void openSettingsFragment(){
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() < 1 & !isSettingsFragment){
            isNoteFragment = false;
            isSettingsFragment = true;
            isCreateFragment = false;
            isSyncFragment = false;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (!isSettingsFragment){
            clearFirstFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void openSynFragment() {
        Log.d(MAIN_RRESENTER_TAG, "Stack size = "+fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() < 1 & !isSyncFragment){
            isNoteFragment = false;
            isSettingsFragment = false;
            isCreateFragment = false;
            isSyncFragment = true;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new SyncInfoFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (!isSyncFragment){
            clearFirstFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, new SyncInfoFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void clearFirstFragment(){
        if (fragmentManager.getBackStackEntryCount() != 0)
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void closeDB() {
        model.close();
    }
}
