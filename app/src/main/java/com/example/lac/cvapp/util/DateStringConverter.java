package com.example.lac.cvapp.util;

public class DateStringConverter {

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
        return null;.
    }
}
