package com.doppelgunner.doppeleater.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by robertoguazon on 06/01/2017.
 */
public class DateHandler {

    public static final DateType[] DATE_TYPES = {
            DateType.SEC,
            DateType.MIN,
            DateType.HOUR,
            DateType.DAY,
            DateType.WEEK,
            DateType.MONTH,
            DateType.YEAR,
            DateType.LIFETIME,
    };

    public enum DateType {
        SEC(1000L),
        MIN(60000L),
        HOUR(3600000L),
        DAY(86400000L),
        WEEK(604800000L),
        MONTH(2419200000L),
        YEAR(29030400000L),
        LIFETIME(-1L);

        private long value;

        DateType(long value) {
            this.value = value;
        }

        public long getValue() {return value;}
    }

    /*
    public static final long SEC = 1000L;
    public static final long MIN = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    public static final long MONTH = 2419200000L;
    public static final long YEAR = 29030400000L;
    */

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_LOCAL_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String TIME_STARTED_DATE_FORMAT = "MMMMM dd, yyyy";
    public static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM--dd";

    private static DateFormat createDateFormat(String dateFormatString) {
        return new SimpleDateFormat(dateFormatString);
    }

    public static Date getDateNow() {
        Date date = new Date();
        return date;
    }

    public static long getDateAgo(long dateType) {
        if (dateType <= 0) return -1;
        return new Date().getTime() - dateType;
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param dateString yyyy-MM-dd HH:mm:ss
     * */
    public static Date convertDate(String dateString) {
        return convertDate(dateString, DEFAULT_DATE_FORMAT);
    }

    public static Date convertBirthDate(String dateString) {
        return convertDate(dateString, BIRTHDAY_DATE_FORMAT);
    }

    public static Date convertDate(String dateString, String DATE_FORMAT) {
        if (Validator.isEmpty(dateString)) return null;

        DateFormat df = createDateFormat(DATE_FORMAT);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertDate(long dateLong) {
        Date date = new Date(dateLong);
        return convertDate(date);
    }

    public static String convertDate(Date date) {
        return convertDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String convertBirthDate(Date date) {
        return convertDate(date, BIRTHDAY_DATE_FORMAT);
    }

    public static String convertDate(Date date, String DATE_FORMAT) {
        if (date == null) return null;

        DateFormat df = createDateFormat(DATE_FORMAT);
        String dateString = null;
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     *@param conversion - DateHandler.HOUR
    * @return converted ago date
    * */

    public static double convertLongDateToAgo(long createdDate, long conversion) {
        return convertLongDateToAgo(createdDate, new Date().getTime(), conversion);
    }
    public static double convertLongDateToAgo(long createdDate, long timeNow, long conversion) {
        Long timeElapsed = timeNow - createdDate;

        double hours = (double)timeElapsed / conversion;
        hours = hours * 100;
        hours = Math.round(hours);
        hours = hours /100;

        return hours;
    }

    /**
     * @see <a href="https://goo.gl/1hX4GM">stackoverflow link</a>
     * @return ago date
     */
    public static String convertLongDateToAgoString(Date createdDate, Date timeNow) {
        return convertLongDateToAgoString(createdDate.getTime(), timeNow.getTime());
    }

    /**
     * @see <a href="https://goo.gl/1hX4GM">stackoverflow link</a>
     * @return ago date
     * */
    public static String convertLongDateToAgoString (long createdDate, long timeNow){
        Long timeElapsed = timeNow - createdDate;

        // For logging in Android for testing purposes
        /*
        Date dateCreatedFriendly = new Date(createdDate);
        Log.d("MicroR", "dateCreatedFriendly: " + dateCreatedFriendly.toString());
        Log.d("MicroR", "timeNow: " + timeNow.toString());
        Log.d("MicroR", "timeElapsed: " + timeElapsed.toString());*/

        // Lengths of respective time durations in Long format.

        String finalString = "0sec";
        String unit;

        if (timeElapsed < DateType.MIN.getValue()){
            // Convert milliseconds to seconds.
            double seconds = (double) (timeElapsed / DateType.SEC.getValue());
            finalString = getUnitFormat(seconds,"sec");
        } else if (timeElapsed < DateType.HOUR.getValue()) {
            double minutes = (double) (timeElapsed / DateType.MIN.getValue());
            finalString = getUnitFormat(minutes,"min");
        } else if (timeElapsed < DateType.DAY.getValue()) {
            double hours   = (double) (timeElapsed / DateType.HOUR.getValue());
            finalString = getUnitFormat(hours,"hour");
        } else if (timeElapsed < DateType.WEEK.getValue()) {
            double days   = (double) (timeElapsed / DateType.DAY.getValue());
            finalString = getUnitFormat(days,"day");
        } else if (timeElapsed < DateType.MONTH.getValue()) {
            double weeks = (double) (timeElapsed / DateType.WEEK.getValue());
            finalString = getUnitFormat(weeks,"week");
        } else if (timeElapsed < DateType.YEAR.getValue()) {
            double months = (double) (timeElapsed / DateType.MONTH.getValue());
            finalString = getUnitFormat(months,"month");
        } else if (timeElapsed >= DateType.YEAR.getValue()) {
            double years = (double) (timeElapsed / DateType.YEAR.getValue());
            finalString = getUnitFormat(years,"year");
        }


        return finalString + " ago";
    }

    private static String getUnitFormat(double value, String unit) {
        value = Math.round(value);
        String timeFormat;
        if (value == 1) {
            timeFormat =  " " + unit;
        } else {
            timeFormat = " " + unit + "s";
        }

        timeFormat = String.format("%.0f", value) + timeFormat;
        return timeFormat;
    }

}
