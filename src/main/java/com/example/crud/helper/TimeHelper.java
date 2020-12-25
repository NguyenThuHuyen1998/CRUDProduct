package com.example.crud.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/*
    created by HuyenNgTn on 12/12/2020
*/
public class TimeHelper {
    public static final Logger logger = LoggerFactory.getLogger(TimeHelper.class);

    private static TimeHelper timeHelper= new TimeHelper();
    private static Calendar calendar= Calendar.getInstance();
    public static TimeHelper getInstance(){
        return timeHelper;
    }

    public long convertTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date= simpleDateFormat.parse(dateStr);
        return date.getTime();
    }

    public String getFirstDayInWeek(){
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        calendar.add(Calendar.DATE, -day.getValue()+1);
        Calendar c = Calendar.getInstance();
        String month =String.valueOf(calendar.get(Calendar.MONTH)+ 1);
        String year;
        int dayOfMonth= calendar.get(Calendar.DAY_OF_MONTH);
        String date= String.valueOf(dayOfMonth);
        if(dayOfMonth<10){
            StringBuilder sb= new StringBuilder("0");
            sb.append(String.valueOf(dayOfMonth));
            date= sb.toString();
        }
        if(calendar.get(Calendar.MONTH)<9){
            StringBuilder sb= new StringBuilder("0");
            sb.append(String.valueOf(calendar.get(Calendar.MONTH)));
            month= sb.toString();
        }
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        year= String.valueOf(calendar.get(Calendar.YEAR));
        System.out.println("Date: " + date+ "/"+ month+"/"+ year);
        return date+"/"+month+"/"+ year;
    }

    public static void main(String[] args) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        System.out.println(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        System.out.println(calendar.getTime());
//        calendar.add(calendar.getFirstDayOfWeek(), 7);

    }

    public String getLastDayInWeek(){
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        calendar.add(Calendar.DATE, 7-day.getValue());
        Calendar c = Calendar.getInstance();
        String month;
        String year;
        int dayOfMonth= calendar.get(Calendar.DAY_OF_MONTH);
        String date= String.valueOf(dayOfMonth);
        if(dayOfMonth<10){
            StringBuilder sb= new StringBuilder("0");
            sb.append(String.valueOf(dayOfMonth));
            date= sb.toString();
        }
        if(calendar.get(Calendar.MONTH)<9){
            StringBuilder sb= new StringBuilder("0");
            sb.append(String.valueOf(calendar.get(Calendar.MONTH)));
            month= sb.toString();
        }
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        month= String.valueOf(calendar.get(Calendar.MONTH)+ 1);
        year= String.valueOf(calendar.get(Calendar.YEAR));
        System.out.println("Date: " + date+ "/"+ month+"/"+ year);
        return date+"/"+month+"/"+ year;
    }


    public String getFirstInMonth(){
        int month= calendar.get(Calendar.MONTH)+1;
        int year= calendar.get(Calendar.YEAR);
        return "01/"+ String.valueOf(month)+"/"+String.valueOf(year);
    }

    public String getLastDayInMonth(){
        Calendar calendar= Calendar.getInstance();
        int lastDayOfMonth= calendar.getActualMaximum(Calendar.DATE);
        int month= calendar.get(Calendar.MONTH)+ 1;
        if(month<10){
            return String.valueOf(lastDayOfMonth)+"/0"+String.valueOf(month)+ "/"+ String.valueOf(calendar.get(Calendar.YEAR));
        }
        return String.valueOf(lastDayOfMonth)+"/"+ String.valueOf(month)+ "/"+ String.valueOf(calendar.get(Calendar.YEAR));
    }

    public String getFirstDayOfYear(){
        Calendar calendar= Calendar.getInstance();
        return "01/01/"+ calendar.get(Calendar.YEAR);
    }

    public String getLastDayOfYear(){
        Calendar calendar= Calendar.getInstance();
        return "31/12/"+ calendar.get(Calendar.YEAR);
    }

}

