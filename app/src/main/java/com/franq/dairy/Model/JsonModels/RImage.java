package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RImage {

    @SerializedName("note_id")
    @Expose
    private String note_id;

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }
}
