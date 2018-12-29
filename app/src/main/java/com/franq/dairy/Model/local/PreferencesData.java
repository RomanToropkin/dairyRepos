package com.franq.dairy.Model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Хранилище настроек. Базовый способ хранения внутренних данных
 */
public class PreferencesData {

    public static String baseURL = "http://192.168.1.217:8080/";

    /**Ключ к хранилищу логина и пароля*/
    private static final String PREFERENCES = "app";
    /**Ключ к логину*/
    private static final String PREFERENCES_LOGIN = "login";
    /**Ключ к паролю*/
    private static final String PREFERENCES_PASS = "pass";

    private static final String PREFERENCES_IP = "ip";
    /**
     * Ключ к куки
     */
    private static final String PREFERENCES_UID = "uid";
    /**Объект, взаимодействуюший в хранилищем*/
    private SharedPreferences sharedPreferences;
    private static volatile PreferencesData instance;
    
    public static final String DEFAULT_LOGIN = "guest";

    /**Конструктор - инициализвция объекта хранилища*/
    private PreferencesData(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

    }

    public static PreferencesData getInstance(Context context) {
        PreferencesData localInstance = instance;
        if ( localInstance == null ) {
            synchronized (PreferencesData.class) {
                localInstance = instance;
                if ( localInstance == null ) {
                    instance = localInstance = new PreferencesData( context );
                }
            }
        }
        return localInstance;
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
        return sharedPreferences.getString(PREFERENCES_LOGIN, DEFAULT_LOGIN);
    }

    /**Возвращает пароль
     * @return пароль*/
    public String getPass() {
        return sharedPreferences.getString(PREFERENCES_PASS, "");
    }

    public void addCookie(String cookie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCES_UID, cookie)
                .apply();
    }

    public void addIp(String ip){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCES_IP, ip)
                .apply();
    }

    public String getIp() {
        baseURL = sharedPreferences.getString( PREFERENCES_IP, baseURL );
        return baseURL;
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
        editor
                .putString( PREFERENCES_LOGIN, DEFAULT_LOGIN )
                .remove(PREFERENCES_PASS)
                .remove(PREFERENCES_UID)
                .apply();
    }
}
