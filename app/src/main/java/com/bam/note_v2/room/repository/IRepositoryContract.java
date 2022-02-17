package com.bam.note_v2.room.repository;

import androidx.lifecycle.LiveData;

import com.bam.note_v2.room.NoteEntity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public interface IRepositoryContract {

    LiveData<List<NoteEntity>> getAllInLiveData() throws IOException, XmlPullParserException;
    void insert(NoteEntity note);
    void delete(NoteEntity note);
    void createNote();
    void getNoteById(int id);

    void setAsyncTaskCallBack(NoteRepository.AsyncTaskCallBack asyncTaskCallBack);
}
