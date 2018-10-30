package com.franq.dairy.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Хранилище настроек. Базовый способ хранения внутренних данных
 */
public class PreferencesData {
    /**Ключ к хранилищу логина и пароля*/
    private static final String PREFERENCES = "app";
    /**Ключ к логину*/
    private static final String PREFERENCES_LOGIN = "login";
    /**Ключ к паролю*/
    private static final String PREFERENCES_PASS = "pass";
    /**
     * Ключ к куки
     */
    private static final String PREFERENCES_UID = "uid";
    /**Объект, взаимодействуюший в хранилищем*/
    private SharedPreferences sharedPreferences;

    /**Конструктор - инициализвция объекта хранилища*/
    public PreferencesData(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

    }

    /**Добавление логина в хранилище
     * @param login логин
     * @param pass пароль*/
    public void addLoginPass(String login, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCES_LOGIN, login);
        editor.putString(PREFERENCES_PASS, pass)
                .apply();
    }

    /**Возвращает логин
     * @return логин*/
    public String getLogin() {
        return sharedPreferences.getString(PREFERENCES_LOGIN, "");
    }

    /**Возвращает пароль
     * @return пароль*/
    public String getPass() {
        return sharedPreferences.getString(PREFERENCES_PASS, "");
    }

    public void addCookie(String cookie) {
        Log.d("cookie", "куки получил : " + cookie);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCES_UID, cookie)
                .apply();
    }

    /**
     * Вовзращает куки
     *
     * @return куки
     */
    public String getCookie() {
        Log.d("cookie", "куки отдал : " + sharedPreferences.getString(PREFERENCES_UID, ""));
        return sharedPreferences.getString(PREFERENCES_UID, "");
    }

    /**
     * Очищает кэш данных
     */
    public void clearData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREFERENCES_LOGIN)
                .remove(PREFERENCES_PASS)
                .remove(PREFERENCES_UID)
                .apply();
    }
}
