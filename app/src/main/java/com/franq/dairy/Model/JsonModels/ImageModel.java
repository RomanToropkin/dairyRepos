package com.franq.dairy.Model.JsonModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImageModel extends RealmObject {

    @PrimaryKey
    private String id;

    private String noteId, imageURI;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
