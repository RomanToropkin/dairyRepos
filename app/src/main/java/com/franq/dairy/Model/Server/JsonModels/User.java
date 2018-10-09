package com.franq.dairy.Model.Server.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("pass")
    @Expose
    private String pass;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

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