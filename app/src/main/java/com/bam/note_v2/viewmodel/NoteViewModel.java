package com.bam.note_v2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bam.note_v2.edit.customview.INoteBodyElement;
import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.room.repository.IRepositoryContract;
import com.bam.note_v2.room.repository.NoteRepository;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel implements IViewModelContract.NoteEditFragment, IViewModelContract.NoteListFragment {

    private LiveData<List<NoteEntity>> __notes;

    private MutableLiveData<NoteEntity> __selectedNote;

    private int __position = -1;


    private MutableLiveData<List<INoteBodyElement>> __noteBodyStructure;
    private String __title;
    private final IRepositoryContract __repository;


    public NoteViewModel(@NonNull Application application) {
        super(application);

        LocalDataBase _localDataBase = LocalDataBase.getDatabase(application.getApplicationContext());
        __repository = new NoteRepository(_localDataBase.noteDao());

        __repository.setAsyncTaskCallBack(note -> {
            getSelectedNote().setValue(note);
        });
    }

    @Override
    public int getPosition() {
        __position++;
        return __position;
    }

    @Override
    public void resetPosition() {
        __position = -1;
    }

    @Override
    public void setTitle(String title) {
        __title = title;
    }

    @Override
    public MutableLiveData<List<INoteBodyElement>> getNoteBodyStructure() {
        if (__noteBodyStructure == null)
        {
            __noteBodyStructure = new MutableLiveData<>();
        }
        return __noteBodyStructure;
    }

    @Override
    public MutableLiveData<NoteEntity> getSelectedNote(){
        if (__selectedNote == null)
        {
            __selectedNote = new MutableLiveData<>();
        }
        return  __selectedNote;
    }


    @Override
    public LiveData<List<NoteEntity>> getAllNotes() throws IOException, XmlPullParserException {

        if (__notes == null) {
            __notes = __repository.getAllInLiveData();
        }
        return __notes;
    }

    @Override
    public void saveNote() {

        List<SpannableElement> _body = new ArrayList<>();
        for (INoteBodyElement e: __noteBodyStructure.getValue())
        {
            _body.addAll(e.getContent());
        }

        getSelectedNote().getValue().setTitle(__title);
        getSelectedNote().getValue().setBody(_body);

        __repository.insert(__selectedNote.getValue());

    }


    @Override
    public void editNote(NoteEntity note) {

       __repository.getNoteById(note.getId());
    }

    @Override
    public void deleteNote(NoteEntity note) {
        __repository.delete(note);
    }

    @Override
    public void createNote() {
        __repository.createNote();
    }

}
