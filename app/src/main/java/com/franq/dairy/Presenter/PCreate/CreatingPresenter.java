package com.franq.dairy.Presenter.PCreate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Интерфейс, описывающий представителя создания записи
 */
public interface CreatingPresenter {
    void createNote(String title, String description, ArrayList <String> imagesUri) throws IOException;

}
