package com.franq.dairy.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteDate {

    private static String stringDate;
    private static SimpleDateFormat noteFormat = new SimpleDateFormat( "d.M.yyyy" );
    private static SimpleDateFormat fullNoteFormat = new SimpleDateFormat( "d.M.yyyy HH:MM:SS" );
    private static Date date;

    public static String getNowDate() {
        date = new Date( );
        stringDate = noteFormat.format( date );
        return stringDate;
    }

    public static String getPickedDate() {
        return stringDate != null ? stringDate : getNowDate( );
    }

    public static void setPickedDate(String date) {
        stringDate = date;
    }

    public static String getFullDate() {
        if ( date == null )
            date = new Date( );
        return fullNoteFormat.format( date );
    }

}
