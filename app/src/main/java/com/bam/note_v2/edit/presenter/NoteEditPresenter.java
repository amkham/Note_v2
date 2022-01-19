package com.bam.note_v2.edit.presenter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.room.Room;

import com.bam.note_v2.R;
import com.bam.note_v2.edit.custom.NoteView;
import com.bam.note_v2.edit.NoteEditFragment;
import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteDao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class NoteEditPresenter implements INoteEditPresenter{

    private NoteEditFragment fragment;

    NoteView mNoteView;

    private String mTitle = "Пустая заметка";


    public NoteEditPresenter(NoteEditFragment fragment)
    {
        this.fragment = fragment;
        mNoteView = new NoteView(fragment.requireContext());
    }





    @Override
    public void onBolt() {

        mNoteView.onBolt();
    }

    @Override
    public void onItalic() {
        mNoteView.onItalic();
    }

    @Override
    public void onUnder() {
       mNoteView.onUnder();
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



    @Override
    public void addImage(Uri uri, int start, String txt) throws FileNotFoundException {

        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(txt);

        ImageSpan imageSpan = new ImageSpan( fragment.requireContext(),uri);

        ssBuilder.setSpan(imageSpan, start,start+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragment.setImage(ssBuilder);
    }








}
