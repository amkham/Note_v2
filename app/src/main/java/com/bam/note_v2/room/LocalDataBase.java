package com.bam.note_v2.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bam.note_v2.room.converters.ElementTypeConverter;
import com.bam.note_v2.room.converters.SpannableElementsConverter;

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters({ElementTypeConverter.class, SpannableElementsConverter.class})
public abstract class LocalDataBase extends RoomDatabase {

        public abstract NoteDao noteDao();

        private static LocalDataBase INSTANCE;

        public static LocalDataBase getDatabase(final Context context) {
                if (INSTANCE == null) {
                        synchronized (LocalDataBase.class) {
                                if (INSTANCE == null) {
                                        SpannableElementsConverter _converter = new SpannableElementsConverter(context);

                                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                LocalDataBase.class, "notes")
                                                .addTypeConverter(_converter)
                                                .build();
                                }
                        }
                }
                return INSTANCE;
        }
}
