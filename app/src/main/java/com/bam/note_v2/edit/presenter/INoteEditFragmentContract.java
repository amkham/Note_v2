package com.bam.note_v2.edit.presenter;

import android.content.Context;
import android.view.View;

import com.bam.note_v2.edit.custom.INoteBodyElement;
import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.room.repository.IRepositoryContract;

import java.io.IOException;
import java.util.List;

public interface INoteEditFragmentContract extends IRepositoryContract {

    interface EditFragment
    {
        void setTitle(String txt);
        void setBody(List<INoteBodyElement> elements) throws IOException;
        
        Context getContext(Context context);

    }

    interface Presenter
    {
        void addView(View element);
        void deleteView(View element);
        void saveNote(NoteEntity noteEntity);
    }

}
