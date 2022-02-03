package com.bam.note_v2.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bam.note_v2.edit.custom.spannable.SpannableElement;
import com.bam.note_v2.room.converters.SpannableElementsConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class NoteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;

    @TypeConverters({SpannableElementsConverter.class})
    private List<SpannableElement> body = new ArrayList<>();

    private String mDate;

    public NoteEntity()
    {
        title = "Пустая заметка";
        updateDate();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
