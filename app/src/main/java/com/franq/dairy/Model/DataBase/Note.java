package com.franq.dairy.Model.DataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    private String id;
    private String  title;
    private String description;
    private String date;
    private String imageUMI;

    public String getId()  {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUMI() {
        return imageUMI;
    }

    public void setImageUMI(String imageUMI) {
        this.imageUMI = imageUMI;
    }
}
