package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientDate {

    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
