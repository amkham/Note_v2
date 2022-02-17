package com.bam.note_v2.room.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bam.note_v2.room.NoteDao;
import com.bam.note_v2.room.NoteEntity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class NoteRepository implements IRepositoryContract {


    public interface AsyncTaskCallBack
    {
        void finish(NoteEntity note);
    }

    private AsyncTaskCallBack __asyncTaskCallBack;
    public void setAsyncTaskCallBack(AsyncTaskCallBack asyncTaskCallBack) {
        __asyncTaskCallBack = asyncTaskCallBack;
    }

    private final NoteDao __noteDao;

    public NoteRepository(NoteDao noteDao) {
        __noteDao = noteDao;
    }



    @Override
    public LiveData<List<NoteEntity>> getAllInLiveData() throws IOException, XmlPullParserException {
        return __noteDao.getAllInLiveData();
    }


    @Override
    public void insert(NoteEntity note) {

        new Thread(() -> {
         __noteDao.insert(note);
        }).start();
    }

    @Override
    public void delete(NoteEntity note) {
        new Thread(() -> __noteDao.delete(note)).start();
    }

    @Override
    public void createNote() {

        new CreateNoteTask().execute();
    }

    @Override
    public void getNoteById(int id) {

        new GetNoteByIdTask().execute(id);

    }

    private class GetNoteByIdTask extends AsyncTask<Integer, Void, NoteEntity>
    {


        @Override
        protected NoteEntity doInBackground(Integer... integers) {

            return __noteDao.getNoteById(integers[0]);
        }

        @Override
        protected void onPostExecute(NoteEntity noteEntity) {
            __asyncTaskCallBack.finish(noteEntity);
        }
    }

    private  class CreateNoteTask extends AsyncTask<Void, Void, NoteEntity>
    {


        @Override
        protected NoteEntity doInBackground(Void... voids) {
            __noteDao.insert(new NoteEntity());
            return __noteDao.getLastCreatedNote();
        }

        @Override
        protected void onPostExecute(NoteEntity noteEntity) {
            __asyncTaskCallBack.finish(noteEntity);
        }
    }


}
