package com.bam.note_v2.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.room.converters.SpannableElementsConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class NoteEntity implements Serializable, Comparable<NoteEntity> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    @TypeConverters({SpannableElementsConverter.class})
    private List<SpannableElement> body = new ArrayList<>();


    private int day =0, month = 0, year = 0, hour = 0, min = 0, sec = 0;

    public NoteEntity()
    {
        title = "Заметка";
        updateDate();

    }

    public NoteEntity(int id, String title, List<SpannableElement> body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getChangeTime()
    {
        String _hour = String.valueOf(hour);
        String _min = String.valueOf(min);

        if (hour / 10 < 1)
        {
            _hour = "0" + _hour;
        }

        if (min/10 <1)
        {
            _min = "0" + min;
        }

        return _hour + ":" + _min;
    }


    public String getChangeDate()
    {
        String _day = String.valueOf(day);
        String _month = String.valueOf(month);

        if (day/10 <1)
        {
            _day = "0" + _day;
        }

        if(month /10 < 1)
        {
            _month = "0" + _month;
        }
        return _day + "/" + _month + "/" + year;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public List<SpannableElement> getBody() {
        return body;
    }

    public void setBody(List<SpannableElement> body) {
        this.body = body;
    }

    public void updateDate()
    {

        Calendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        sec = calendar.get(Calendar.SECOND);

    }


    @Override
    public int compareTo(NoteEntity o) {
        Calendar _calendar1 = new GregorianCalendar();
        Calendar _calendar2 = new GregorianCalendar();
        _calendar1.set(year, month, day, hour, min, sec);
        _calendar2.set(o.year, o.month, o.day, o.hour, o.min, o.sec);

        return _calendar2.compareTo(_calendar1);
    }
}
