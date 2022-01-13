package com.bam.note_v2.main.presenter;

import android.os.AsyncTask;

import androidx.room.Room;

import com.bam.note_v2.main.NoteListFragment;
import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteDao;
import com.bam.note_v2.room.NoteEntity;

import java.util.List;

public class MainFragmentPresenter implements IMainFragmentPresenter{

    private final NoteListFragment noteListFragment;


    public MainFragmentPresenter(NoteListFragment fragment)
    {
        noteListFragment = fragment;
    }

    @Override
    public void createNote() {

    }

    @Override
    public void editNote(NoteEntity note) {

    }

    @Override
    public void getAllNote() {

        NoteTask noteTask = new NoteTask();
        noteTask.execute();
    }


    class NoteTask extends AsyncTask<Void, Void, List<NoteEntity>>
    {

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            LocalDataBase db = Room.databaseBuilder(noteListFragment.requireContext(), LocalDataBase.class, "note").build();
            NoteDao statisticDao = db.noteDao();

            List<NoteEntity> result = statisticDao.getAll();
            if (result.size() == 0)
            {
                for (int i=0; i < 15; i++)
                {
                    statisticDao.insert(new NoteEntity("title " +  i, "body " + i));
                    result = statisticDao.getAll();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<NoteEntity> noteEntities) {
            super.onPostExecute(noteEntities);
            noteListFragment.createNoteList(noteEntities);

        }
    }

}


