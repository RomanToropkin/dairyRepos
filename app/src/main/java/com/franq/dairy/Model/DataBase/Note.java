package com.franq.dairy.Model.DataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Объект, моделирущий запись и применяемый в Realm ORM
 */
public class Note extends RealmObject {

    /**
     * Первичный ключ
     */
    @PrimaryKey
    private String id;
    /**Столбец - заголовок */
    private String title;
    /**Столбец - основной текстовый контент записи */
    private String description;
    /**Столбец - дата в формате д:ММ:ГГГГ чч:мм  */
    private String date;
    /**Столбец - местоположение изображений */
    private String imageUMI;

    /**Возвращает ключ
     * @return id - первичный ключ */
    public String getId()  {
        return id;
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

    /**Возвращает местоположение изображений
     * @return imageUMI - местоположение изображений */
    public String getImageUMI() {
        return imageUMI;
    }

    /**Устанавливает местоположение изображений
     * @param imageUMI местопоожение */
    public void setImageUMI(String imageUMI) {
        this.imageUMI = imageUMI;
    }
}
