package com.franq.dairy.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesData {

    private static final String PREFERENCES = "app";
    private static final String PREFERENCES_LOGIN = "login";
    private static final String PREFERENCES_PASS = "pass";

    private SharedPreferences sharedPreferences;

    public PreferencesData(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

    }

    public void addLoginPass(String login, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCES_LOGIN, login);
        editor.putString(PREFERENCES_PASS, pass);
        editor.apply();
    }

    public String getLogin() {
        return sharedPreferences.getString(PREFERENCES_LOGIN, "");
    }

    public String getPass() {
        return sharedPreferences.getString(PREFERENCES_PASS, "");
    }

}
