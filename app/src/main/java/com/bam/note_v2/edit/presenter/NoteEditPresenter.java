package com.bam.note_v2.edit.presenter;

import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.room.Room;

import com.bam.note_v2.edit.custom.spannable.SpannableImage;
import com.bam.note_v2.edit.custom.view.NoteView;
import com.bam.note_v2.edit.NoteEditFragment;
import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteDao;

import java.io.FileNotFoundException;

public class NoteEditPresenter implements INoteEditPresenter{

    private NoteEditFragment fragment;


    private String mTitle = "Пустая заметка";


    public NoteEditPresenter(NoteEditFragment fragment)
    {
        this.fragment = fragment;
    }






    @Override
    public void changeTitle(String s) {
        mTitle = s;
    }




    @Override
    public void save() {

       LocalDataBase db = Room.databaseBuilder(fragment.requireContext(), LocalDataBase.class, "note").build();
       NoteDao statisticDao = db.noteDao();

//        NoteEntity note = new NoteEntity(mTitle, textEditor.getText());
//
//        new Thread(() -> statisticDao.insert(note)).start();


    }










}
