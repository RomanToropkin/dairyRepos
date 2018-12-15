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

import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Presenter.PMain.MainPresenterImpl;
import com.franq.dairy.R;
import com.franq.dairy.Utility.NoteDate;
import com.franq.dairy.View.Contracts.MainContractVIew;
import com.franq.dairy.View.Dialogs.DatePickerDialog;
import com.franq.dairy.View.Fragments.BlankNoteFragment;
import com.franq.dairy.View.Fragments.CreatingFragment;
import com.franq.dairy.View.Fragments.DairyNoteFragment;
import com.franq.dairy.View.Fragments.LoginFragment;
import com.franq.dairy.View.Fragments.NoteFragment;
import com.franq.dairy.View.Fragments.RegisterFragment;
import com.franq.dairy.View.Fragments.SettingsFragment;
import com.franq.dairy.View.Fragments.SyncInfoFragment;

/*
 **Главная активность. Является связующим элементом между фрагментами. Взаимодействует с ними с помощью механизма обратного вызова.
 **/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoteFragment.OnListFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, MainContractVIew, SyncInfoFragment.onSynchFragmentInteractionListener,
        CreatingFragment.onCreatingFragmentInteractionListener, DatePickerDialog.onDatePickListener, DairyNoteFragment.onDairyNoteFragmentInteraction,
        LoginFragment.OnFragmentInteractionListener, RegisterFragment.onRegisterFragmentInteraction {
    /**
     * Тэг для откладки
     */
    private static final String MAIN_ACTIVITY_TAG = "mainActicity";
    /**Представление*/
    private MainPresenterImpl presenter;
    /**
     * Оторжание календаря
     */
    private boolean isCalendarEnable = true;
    /**Плавающая кнопка*/
    private FloatingActionButton fab;
    /**
     * Пустой фрагмент для "нулевых" записей
     */
    private BlankNoteFragment noteFragment;

    /**Замена фрагмента логирования на фрагмент синхронизации*/
    @Override
    public void onLoginInteract() {
        getSupportFragmentManager().popBackStackImmediate();
        chooseSyncInfoFragment();
    }

    /**Замена фрагмента синхронизации на фрагмент логирования*/
    @Override
    public void onLogoutInteract() {
        getSupportFragmentManager().popBackStackImmediate();
        chooseLoginFragment();
    }
    /**Создание акттивность*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents(null);

    }

    /**Обработка нажатия на кнопку "Назад"*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /**Инициализация меню в action bar*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**Изменение отображения элементов меню в action bar*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isCalendarEnable){
            menu.findItem(R.id.action_calendar).setVisible(true);
        } else {
            menu.findItem(R.id.action_calendar).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**Обработка нажатия на иконку в меню(иконка календаря)*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_calendar) {
            DialogFragment datePicker = new DatePickerDialog( NoteDate.getPickedDate( ) );
            datePicker.show(getSupportFragmentManager(), "datePicker");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**Отображение элементов (плавающая кнопка и календарь)*/
    @Override
    public void showMainView() {
        isCalendarEnable = true;
        invalidateOptionsMenu();
        fab.setVisibility(View.VISIBLE);
    }

    /**Сокрытие элементов (плавающая кнопка и календарь)*/
    @Override
    public void hideMainView() {
        isCalendarEnable = false;
        invalidateOptionsMenu();
        fab.setVisibility(View.INVISIBLE);
    }

    /**Замена текущего фрагмента на фрагмент Записи*/
    @Override
    public void chooseNoteFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    /**
     * Отображение стартового фрагмента (Фрагмент записи)
     */
    private void startFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    /**Замена текущего фрагмента на фрагмент выбранной записи
     * @param item выбранная запись в списке*/
    @Override
    public void chooseDairyNoteFragment(Note item) {
        DairyNoteFragment fragment = new DairyNoteFragment();
        fragment.setNote(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, fragment)
                .addToBackStack("stack")
                .commit();
    }

    /**Замена текущего фрагмента на фрагмент настройки*/
    @Override
    public void chooseSettingsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new SettingsFragment())
                .commit();
    }

    /**Замена текущего фрагмента на фрагмент создания записи*/
    @Override
    public void chooseCreatingFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new CreatingFragment())
                .addToBackStack("stack")
                .commit();
    }

    /**Замена текущего фрагмента на фрагмент логирования*/
    @Override
    public void chooseLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new LoginFragment())
                .addToBackStack("stack")
                .commit();
    }

    /**Замена текущего фрагмента на фрагмент регистрации*/
    @Override
    public void chooseRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new RegisterFragment())
                .addToBackStack("stack")
                .commit();
    }

    /**
     * Удалить пустой фрагмент (отсутствия записей)
     */
    @Override
    public void hideBlankFragment() {
        getSupportFragmentManager().beginTransaction()
                .remove(noteFragment)
                .commit();
    }

    /**
     * Изменение статуса пустого фрагмента
     *
     * @param flag true - показать фрагмент, false - скрыть фрагмент
     */
    @Override
    public void onBlankFragmentStatusChange(boolean flag) {
        if (flag)
            showBlankFragment();
        else
            hideBlankFragment();
    }

    /**
     * Отобразить пустой фрагмент (отсутствия записей)
     */
    @Override
    public void showBlankFragment() {
        if (!noteFragment.isAdded())
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.blankContainer, noteFragment)
                    .commit();
    }

    /**
     * Переход от фрагмента создания к фрагменту отображения
     */
    @Override
    public void onDairyDeleteFragment() {
        showMainView();
        getSupportActionBar().setTitle("Мой дневник");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .addToBackStack( "stack" )
                .commit();
    }

    /**
     * Замена текущего фрагмента на фрагмент Записи
     */
    @Override
    public void chooseSyncInfoFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new SyncInfoFragment())
                .commit();
    }

    /**
     * Замена текущего фрагмента на фрагмент Записи
     */
    @Override
    public void onCreatingNote() {
        hideMainView();
        getSupportActionBar().setTitle("Мой дневник");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main_layout, new NoteFragment())
                .commit();
    }

    /**Инициализация компонентов
     * @param view - null, так как не требуется "надувание" элементов*/
    @Override
    public void initComponents(View view) {
        presenter = new MainPresenterImpl();
        presenter.onAttachView(this);

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

        noteFragment = new BlankNoteFragment();
        startFragment();

    }

    /**Обработка нажатия на пункты меню в Navigation view (боковое выползающие меню)
     * @param item нажатый элемент меню*/
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
    /**Обработка нажатия на запись в списке
     * @param item нажатая запись*/
    @Override
    public void onNoteItemLick(Note item) {
        if (item.isValid()) {
            Log.d(MAIN_ACTIVITY_TAG, item.getTitle() + " on Click");
            chooseDairyNoteFragment(item);
        }
    }
    /**Обработка нажатия на плавающую кнопку
     * @param view - элемент кнопки*/
    @Override
    public void onAddButtonClick(View view) {
        chooseCreatingFragment();
    }

    /**
     * Отображения ошибки во всплываюшим баре
     *
     * @param error - описание ошибки
     */
    @Override
    public void showFailError(String error) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main_layout), error, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.show();
    }

    /**
     * Обработка нажатия на дату в календаре
     */
    @Override
    public void onDatePick(int year, int month, int day) {
        presenter.onChangeNoteFragmentData(day, month, year);
    }

    /**
     * Замена регистрационного фрагмента на фрагмент записи
     */
    @Override
    public void onRegisterRefreshInteract() {
        showMainView();
        getSupportActionBar().setTitle("Мой дневник");
        chooseNoteFragment();
    }

    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onSyncInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onCreatingFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onDairyFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onSettingsFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onChooseRegisterFragment() {
        chooseRegisterFragment();
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onNoteFragmentInteract(String name) {
        getSupportActionBar().setTitle(name);
        showMainView();
    }

    /**Вызывается при уничтожении активности*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
    }

    /**Вызывается при остановке активности*/
    @Override
    protected void onStop() {
        super.onStop();
    }
    /**Установка параметров action bar-a и изменение отобржания элементов
     * @#param name заголовок в action bar*/
    @Override
    public void onRegisterFragmentInteract(String name) {
        hideMainView();
        getSupportActionBar().setTitle(name);
    }
}
