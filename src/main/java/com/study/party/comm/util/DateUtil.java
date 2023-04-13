package com.study.party.comm.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String getDateForPattern(String pattern) {
        return getDateForPattern(new Date(), pattern);
    }

    public static String getDateForPattern(Date date, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    public static Date getDateForCalc(int calcType, Date day, int incDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        calendar.add(calcType, incDay);
        return calendar.getTime();
    }

    public static String getSimpleDate() {
        return getDateForPattern("yyyyMMdd");
    }

    public static String getYear() {
        return getDateForPattern("yyyy");
    }

    public static String getMonth() {
        return getDateForPattern("MM");
    }

    public static String getDay() {
        return getDateForPattern("dd");
    }

    public static String getHour() {
        return getDateForPattern("HH");
    }

    public static String getMinute() {
        return getDateForPattern("mm");
    }

    public static String getSecond() {
        return getDateForPattern("ss");
    }

    public static String getSimpleTime() {
        return getSimpleTime(true);
    }

    public static String getSimpleTime(boolean delimiter) {
        if (delimiter) {
            return getDateForPattern("HH:mm:ss");
        } else {
            return getDateForPattern("HHmmss");
        }
    }

    public static String getFullTime() {
        return getDateForPattern("yyyyMMddHHmmss");
    }

    public static int getLastDayOfMonth(int year, int month) {
        LocalDate d = LocalDate.of(year, month, 1);
        return getLastDayOfMonth(d);
    }

    public static int getLastDayOfMonth(LocalDate d) {
        LocalDate lastDay = d.withDayOfMonth(d.getMonth().length(d.isLeapYear()));
        return lastDay.getDayOfMonth();
    }
}
