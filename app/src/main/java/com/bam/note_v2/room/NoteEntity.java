package com.bam.note_v2.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class NoteEntity implements Serializable {

    @PrimaryKey
    private Long id;
    private String mTitle;

    private String mBody;

    private String mDate;

    public NoteEntity(String mTitle, String mBody) {
        this.mTitle = mTitle;
        this.mBody = mBody;
        updateDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public void updateDate()
    {

        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDate = day + "-" + month + "-" + year;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
