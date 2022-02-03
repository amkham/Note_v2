package com.bam.note_v2.main.presenter;

import android.util.Log;

import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteDao;
import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.room.repository.IRepositoryContract;
import com.bam.note_v2.room.repository.NoteRepository;

public class MainFragmentPresenter implements IMainFragmentContract.Presenter{

    private IRepositoryContract.Model  __model;
    private final IMainFragmentContract.View __fragment;

    public MainFragmentPresenter(IMainFragmentContract.View fragment) {
        __fragment = fragment;
        __model = initRepository();

    }


    @Override
    public void onNoteClick(NoteEntity noteEntity) {

    }

    @Override
    public NoteEntity createNote() {
        NoteEntity _noteEntity = new NoteEntity();
        __model.saveCallBack(_noteEntity, () -> {

            Log.i("LOG", "Note save");
        });
        return _noteEntity;
    }


    @Override
    public void loadNotes() {

        __model.getAllCallBack(__fragment::createList);
    }

    private IRepositoryContract.Model initRepository()
    {
        LocalDataBase _localDataBase = LocalDataBase.getDatabase(__fragment.requireContext());
        NoteDao _noteDao = _localDataBase.noteDao();

        return new NoteRepository(_noteDao);
    }
}


