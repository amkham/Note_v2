package com.bam.note_v2.main.presenter;

import android.content.Context;

import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.room.repository.IRepositoryContract;

import java.util.List;

public interface IMainFragmentContract extends IRepositoryContract {

    interface View
    {
        void createList(List<NoteEntity> entities);
        Context requireContext();
    }

    interface Presenter
    {
        void onNoteClick(NoteEntity noteEntity);
        NoteEntity createNote();
        void loadNotes();
    }



}
