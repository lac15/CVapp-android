package com.example.lac.cvapp.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateStringConverter {

    public DateStringConverter() {
    }

    public Date stringToDate(String stringDate, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return format.parse(stringDate);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    public String dateToString(Date date, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return format.format(date);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }
}
