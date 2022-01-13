package com.bam.note_v2.edit.presenter;

import android.net.Uri;
import android.text.InputType;
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

    List<NoteView> noteViews = new ArrayList<>();

    private String mTitle = "Пустая заметка";


    public NoteEditPresenter(NoteEditFragment fragment)
    {
        this.fragment = fragment;
    }





    @Override
    public void onBolt() {
        for (NoteView editText: noteViews)
        {
            editText.onBolt();
        }
    }

    @Override
    public void onItalic() {
        for (NoteView editText: noteViews)
        {
            editText.onItalic();
        }
    }

    @Override
    public void onUnder() {
        for (NoteView editText: noteViews)
        {
            editText.onUnder();
        }
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
    public void addImage(Uri uri) throws FileNotFoundException {

        fragment.addView(createImageView(uri));
        fragment.addView(createEditText());
    }


    public EditText createEditText()
    {
        NoteView editText = new NoteView(fragment.requireContext());
        editText.setBackground(null);
        editText.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        editText.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setPadding(5,5,5,5);

        noteViews.add(editText);

        return editText;
    }

    public ImageView createImageView(Uri uri)
    {
        ImageView imageView = new ImageView(fragment.requireContext(), null, R.style.NoteBodyImage);
        int id = ImageView.generateViewId();
        imageView.setImageURI(uri);
        imageView.setId(id);
        imageView.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setMaxHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setForegroundGravity(Gravity.START);
        imageView.setPadding(5,5,5,5);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);

        ImageSpan imageSpan = new ImageSpan(fragment.requireContext(), uri);


        return imageView;

    }







}
