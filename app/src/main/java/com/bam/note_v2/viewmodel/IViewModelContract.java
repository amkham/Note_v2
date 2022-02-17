package com.bam.note_v2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bam.note_v2.edit.customview.INoteBodyElement;
import com.bam.note_v2.room.NoteEntity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public interface IViewModelContract {

    interface NoteListFragment
    {
        LiveData<List<NoteEntity>> getAllNotes() throws IOException, XmlPullParserException;
        void createNote() throws IOException, XmlPullParserException;
        void editNote(NoteEntity id);
        void deleteNote(NoteEntity note);
    }

    interface  NoteEditFragment
    {
        void saveNote();
        void createNote() throws IOException, XmlPullParserException;

        int getPosition();
        void resetPosition();

        MutableLiveData<NoteEntity> getSelectedNote() throws IOException, XmlPullParserException;

        void setTitle(String title);
        MutableLiveData<List<INoteBodyElement>> getNoteBodyStructure();


    }
}
