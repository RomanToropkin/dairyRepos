package com.franq.dairy.Model.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Объект, моделирущий запись и применяемый в Realm ORM
 */
public class Note extends RealmObject {

    /**
     * Первичный ключ
     */
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    /**Столбец - заголовок */
    @SerializedName("title")
    @Expose
    private String title;
    /**Столбец - основной текстовый контент записи */
    @SerializedName("description")
    @Expose
    private String description;
    /**Столбец - дата в формате д:ММ:ГГГГ чч:мм  */
    @SerializedName("date")
    @Expose
    private String date;
    @Ignore
    @SerializedName("imagesURI")
    @Expose
    private List <String> imageUMI;
    /**Возвращает ключ
     * @return id - первичный ключ */
    public String getId()  {
        return id;
    }

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    /**Возвращает заголовок
     * @return title - заголовок */
    public String getTitle() {
        return title;
    }

    /**Устанавливает заголовок
     * @param title заголовок */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Возвращает контент
     * @return description - основной текстовый контент записи */
    public String getDescription() {
        return description;
    }

    /**Устанавливает контент
     * @param description контекст */
    public void setDescription(String description) {
        this.description = description;
    }

    /**Возвращает дату
     * @return date - дата */
    public String getDate() {
        return date;
    }

    /**Устанавливает дату
     * @param date дата */
    public void setDate(String date) {
        this.date = date;
    }

    public List <String> getImageUMI() {
        return imageUMI;
    }

    public void setImageUMI(List <String> imageUMI) {
        this.imageUMI = imageUMI;
    }
}
