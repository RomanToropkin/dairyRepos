package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MReq {

    @SerializedName("date")
    @Expose

    private String date;
    @SerializedName("result")
    @Expose
    private List <Note> notes = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List <Note> getNotes() {
        return notes;
    }

    public void setNotes(List <Note> notes) {
        this.notes = notes;
    }

}