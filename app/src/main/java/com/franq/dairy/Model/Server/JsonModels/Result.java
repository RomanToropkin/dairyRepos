package com.franq.dairy.Model.Server.JsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Сгенерированный java класс из Json для обработки ответов от сервера.
 *
 * @see com.franq.dairy.Model.Server.APIService
 * Формат json :
 * {
 * "result" : "response"
 * }
 * где response - ответ от сервера на запрос авторизациии и регистрации
 */
public class Result {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
