package com.bam.note_v2.main.presenter;

import com.bam.note_v2.room.NoteEntity;

import java.util.List;

public interface IMainFragmentPresenter {

    void createNote();
    void editNote(NoteEntity note);
    void getAllNote();

}
