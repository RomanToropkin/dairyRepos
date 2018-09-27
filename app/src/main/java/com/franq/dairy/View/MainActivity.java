package com.franq.dairy.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.franq.dairy.Model.Note;
import com.franq.dairy.Model.NotesModel;
import com.franq.dairy.Presenter.PMain.MainPresenterImpl;
import com.franq.dairy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoteFragment.OnListFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, MainContractVIew, SyncInfoFragment.onSynchFragmentInteractionListener,
        CreatingFragment.onCreatingFragmentInteractionListener, DatePickerDialog.onDatePickListener, DairyNoteFragment.onDaityNoteFragmentInteraction{

    private static final String MAIN_ACTIVITY_TAG = "mainActicity";
    private MainPresenterImpl presenter;
    private FragmentManager fragmentManager;
    private String date;
    private boolean isCalculateEnable = true;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (savedInstanceState == null){
            presenter.openStartFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (!isCalculateEnable){
            isCalculateEnable = true;
            invalidateOptionsMenu();
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            getSupportActionBar().setTitle("Мой дневник");
            isCalculateEnable = true;
            invalidateOptionsMenu();
            fab.setVisibility(View.VISIBLE);
            presenter.openNotesFragment();
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setTitle("Настройки");
            isCalculateEnable = false;
            invalidateOptionsMenu();
            fab.setVisibility(View.INVISIBLE);
            presenter.openSettingsFragment();
        } else if (id == R.id.nav_syn) {
            getSupportActionBar().setTitle("Синхронизация");
            isCalculateEnable = false;
            invalidateOptionsMenu();
            fab.setVisibility(View.INVISIBLE);
            presenter.openSynFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onNoteItemLick(Note item) {
        if (item.isValid()) {
            Log.d(MAIN_ACTIVITY_TAG, item.getTitle() + " on Click");
            isCalculateEnable = false;
            invalidateOptionsMenu();
            fab.setVisibility(View.INVISIBLE);
            DairyNoteFragment fragment = new DairyNoteFragment();
            fragment.setNote(item);
            presenter.openDairyNoteFragment(fragment);
        }
    }

    @Override
    public void onNoteFragmentInteractio(String name) {
        getSupportActionBar().setTitle(name);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDairyDeleteFragment() {
        onCreatingNote();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.testServer();
    }

    @Override
    public void onSettingsFragmentInteraction(String name) {
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onCreatingNote() {
        isCalculateEnable = true;
        invalidateOptionsMenu();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddButtonClick(View view) {
        isCalculateEnable = false;
        invalidateOptionsMenu();
        fab.setVisibility(View.INVISIBLE);
        presenter.openAddNoteFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.closeDB();
    }

    @Override
    public void init() {

        fragmentManager = getSupportFragmentManager();

        presenter = new MainPresenterImpl();
        presenter.attachView(this, getSupportFragmentManager());
        presenter.openDB();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.note_add_button);
        fab.setOnClickListener(this::onAddButtonClick);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SimpleDateFormat format = new SimpleDateFormat("dd.M.yyyy");
        date = format.format(new Date());

    }

    @Override
    public void onSynchInteraction(String name) {
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onCreatingFragmentInteraction(String name) {
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onDatePick(int year, int month, int day) {
        date = day+"."+month+"."+year;
        presenter.onChangeDate(day, month, year);
    }

    @Override
    public void onDairyFragmentInteraction(String name) {
        getSupportActionBar().setTitle(name);
    }
}
