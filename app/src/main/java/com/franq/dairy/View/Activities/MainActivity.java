package com.franq.dairy.View.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.franq.dairy.Model.DataBase.Note;
import com.franq.dairy.Presenter.PMain.MainPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.View.Contracts.MainContractVIew;
import com.franq.dairy.View.Dialogs.DatePickerDialog;
import com.franq.dairy.View.Fragments.CreatingFragment;
import com.franq.dairy.View.Fragments.DairyNoteFragment;
import com.franq.dairy.View.Fragments.LoginFragment;
import com.franq.dairy.View.Fragments.NoteFragment;
import com.franq.dairy.View.Fragments.RegisterFragment;
import com.franq.dairy.View.Fragments.SettingsFragment;
import com.franq.dairy.View.Fragments.SyncInfoFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoteFragment.OnListFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, MainContractVIew, SyncInfoFragment.onSynchFragmentInteractionListener,
        CreatingFragment.onCreatingFragmentInteractionListener, DatePickerDialog.onDatePickListener, DairyNoteFragment.onDaityNoteFragmentInteraction,
        LoginFragment.OnFragmentInteractionListener, RegisterFragment.onRegisterFragmentInteraction {

    private static final String MAIN_ACTIVITY_TAG = "mainActicity";
    private MainPresenterImpl presenter;
    private String date;
    private boolean isCalculateEnable = true;
    private FloatingActionButton fab;

    @Override
    public void onLoginInteract() {
        getSupportFragmentManager().popBackStackImmediate();
        chooseSyncInfoFragment();
    }

    @Override
    public void onLogoutInteract() {
        getSupportFragmentManager().popBackStackImmediate();
        chooseLoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents(null);

        if (savedInstanceState == null){
            startFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isCalculateEnable){
            menu.findItem(R.id.action_calendar).setVisible(true);
        } else {
            menu.findItem(R.id.action_calendar).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar) {
            DialogFragment datePicker = new DatePickerDialog(date);
            datePicker.show(getSupportFragmentManager(), "datePicker");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showMainView() {
        isCalculateEnable = true;
        invalidateOptionsMenu();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMainView() {
        isCalculateEnable = false;
        invalidateOptionsMenu();
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void chooseNoteFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void chooseDairyNoteFragment(Note item) {
        DairyNoteFragment fragment = new DairyNoteFragment();
        fragment.setNote(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, fragment)
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void chooseSettingsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new SettingsFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void chooseCreatingFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new CreatingFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void chooseLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new LoginFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void chooseRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new RegisterFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void initComponents(View view) {
        presenter = new MainPresenterImpl();
        presenter.onAttachView(this);
        presenter.openDB();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.note_add_button);
        fab.setOnClickListener(this::onAddButtonClick);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SimpleDateFormat format = new SimpleDateFormat("dd.M.yyyy");
        date = format.format(new Date());

        startFragment();
    }

    @Override
    public void chooseSyncInfoFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new SyncInfoFragment())
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void onRegisterRefreshInteract() {
        showMainView();
        getSupportActionBar().setTitle("Мой дневник");
        chooseNoteFragment();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            chooseNoteFragment();
        } else if (id == R.id.nav_settings) {
            chooseSettingsFragment();
        } else if (id == R.id.nav_syn) {
            presenter.checkAuthorization();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onNoteItemLick(Note item) {
        if (item.isValid()) {
            Log.d(MAIN_ACTIVITY_TAG, item.getTitle() + " on Click");
            chooseDairyNoteFragment(item);
        }
    }

    @Override
    public void onAddButtonClick(View view) {
        chooseCreatingFragment();
    }


    @Override
    public void showFailError(String error) {
        Snackbar.make(findViewById(R.id.content_main_layout), error, Snackbar.LENGTH_LONG).show();
    }

    private void startFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    @Override
    public void onDatePick(int year, int month, int day) {
        date = day+"."+month+"."+year;
        presenter.onChangeDate(day, month, year);
    }

    @Override
    public void onSyncInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onCreatingFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onDairyFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onCreatingNote() {
        hideMainView();
        getSupportActionBar().setTitle("Мой дневник");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    @Override
    public void onSettingsFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onChooseRegisterFragment() {
        chooseRegisterFragment();
    }

    @Override
    public void onNoteFragmentInteract(String name) {
        getSupportActionBar().setTitle(name);
        showMainView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDB();
        presenter.onDetachView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDairyDeleteFragment() {
        showMainView();
        getSupportActionBar().setTitle("Мой дневник");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRegisterFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
}
