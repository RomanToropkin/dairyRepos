package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Сгенерированный java класс из Json для обработки ответов от сервера.
 *
 * @see com.franq.dairy.Model.Server.APIService
 * Формат json :
 * {
 * "login" : "l",
 * "pass" : "p"
 * }
 * где l - логин пользователя
 * p - пароль пользователя
 */
public class User {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("pass")
    @Expose
    private String pass;
    /**@return логин пользователя*/
    public String getLogin() {
        return login;
    }
    /**@param login логин пользователя*/
    public void setLogin(String login) {
        this.login = login;
    }
    /**@return пароль пользователя*/
    public String getPass() {
        return pass;
    }
    /**@param pass пароль пользователя*/
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**Строковое представление Json*/
    @Override
    public String toString() {
        String res = new String()
                .concat("\n{\n")
                .concat("\"")
                .concat("\n     \"login\" : \"")
                .concat(login)
                .concat("\"")
                .concat("\n     \"pass\" : \"")
                .concat(pass)
                .concat("\"")
                .concat("\n}");
        return res;
    }

}