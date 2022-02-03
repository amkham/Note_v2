package com.bam.note_v2.room.repository;

import com.bam.note_v2.room.NoteDao;
import com.bam.note_v2.room.NoteEntity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements IRepositoryContract.Model {


    public interface GetAllCallBack
    {
        void getAll(List<NoteEntity> entities);
    }

    public interface GetByIdCallBack
    {
        void getById(NoteEntity entity);
    }

    public interface SaveCallBack
    {
        void saveNote();
    }


    private GetAllCallBack __getAllCallBack;
    private GetByIdCallBack __getByIdCallBack;
    private SaveCallBack __saveCallBack;
    private final NoteDao __noteDao;

    public NoteRepository(NoteDao noteDao) {
        __noteDao = noteDao;
    }

    @Override
    public void getAllCallBack(GetAllCallBack getAllCallBack) {
        __getAllCallBack = getAllCallBack;
        getAll();
    }

    @Override
    public void getByIdCallBack(Long id, GetByIdCallBack getByIdCallBack) {
        __getByIdCallBack = getByIdCallBack;
        getById(id);

    }

    @Override
    public void saveCallBack(NoteEntity note, SaveCallBack saveCallBack) {
        __saveCallBack = saveCallBack;
        saveNote(note);
    }


    private void getAll() {
        new Thread(() -> {
            try {
                List<NoteEntity> _entities = __noteDao.getAll();
                __getAllCallBack.getAll(_entities);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
                __getAllCallBack.getAll(new ArrayList<>());
            }
        }).start();
    }

    private void getById(Long id) {

        new Thread(() -> {
            try {
                NoteEntity _noteEntity = __noteDao.getById(id);
                __getByIdCallBack.getById(_noteEntity);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
                __getByIdCallBack.getById(null);
            }
        }).start();
    }

    private void saveNote(NoteEntity note) {

        new Thread(() -> {
            __noteDao.insert(note);
            __saveCallBack.saveNote();
        }).start();
    }






}
