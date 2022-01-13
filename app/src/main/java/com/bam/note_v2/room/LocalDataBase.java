package com.bam.note_v2.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class LocalDataBase extends RoomDatabase {

        public abstract NoteDao noteDao();
}
