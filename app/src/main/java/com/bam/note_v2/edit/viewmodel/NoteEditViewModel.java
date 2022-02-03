package com.bam.note_v2.edit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bam.note_v2.edit.custom.INoteBodyElement;

import java.util.List;

public class NoteEditViewModel extends AndroidViewModel {

    private MutableLiveData<List<INoteBodyElement>> __body;

    private String __title;




    public NoteEditViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<INoteBodyElement>> getBody() {

        if(__body == null)
        {
            __body = new MutableLiveData<>();
            loadNote();
        }
        return __body;
    }

    private void loadNote() {
    }
}
