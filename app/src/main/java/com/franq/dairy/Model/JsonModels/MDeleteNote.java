package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDeleteNote {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
