package com.bam.note_v2.room.repository;

import com.bam.note_v2.room.NoteEntity;

public interface IRepositoryContract {


    interface Model
    {
        void getAllCallBack(NoteRepository.GetAllCallBack getAllCallBack);
        void getByIdCallBack(Long id, NoteRepository.GetByIdCallBack getByIdCallBack);
        void saveCallBack(NoteEntity note, NoteRepository.SaveCallBack saveCallBack);

    }


}
