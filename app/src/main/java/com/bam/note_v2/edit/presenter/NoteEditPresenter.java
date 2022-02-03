package com.bam.note_v2.edit.presenter;

import android.view.View;

import com.bam.note_v2.edit.custom.INoteBodyElement;
import com.bam.note_v2.room.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteEditPresenter implements INoteEditFragmentContract.Presenter{


    private List<INoteBodyElement> __noteBodyStructure = new ArrayList<>();


    private INoteEditFragmentContract.EditFragment __editFragment;
    private INoteEditFragmentContract.Model __model;



    public NoteEditPresenter(INoteEditFragmentContract.EditFragment editFragment )
    {
        __editFragment = editFragment;
    }



    public void addBodyElement(INoteBodyElement element)
    {
        __noteBodyStructure.add(element);

    }

    public void deleteBodyElement(INoteBodyElement element)
    {
        __noteBodyStructure.remove(element);
    }


    @Override
    public void addView(View element) {

    }

    @Override
    public void deleteView(View element) {

    }

    @Override
    public void saveNote(NoteEntity noteEntity) {

    }
}
