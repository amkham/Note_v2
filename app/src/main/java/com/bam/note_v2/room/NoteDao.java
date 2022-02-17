package com.bam.note_v2.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    List<NoteEntity> getAll() throws IOException, XmlPullParserException;

    @Query("SELECT * FROM NoteEntity")
    LiveData<List<NoteEntity>> getAllInLiveData() throws IOException, XmlPullParserException;


    @Query("SELECT * FROM NoteEntity WHERE id = (SELECT MAX(id) FROM NoteEntity)")
    NoteEntity getLastCreatedNote();

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    NoteEntity getNoteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Update
    int update(NoteEntity note);


    @Delete
    void delete(NoteEntity note);
}
