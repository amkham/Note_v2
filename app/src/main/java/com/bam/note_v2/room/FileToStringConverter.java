package com.bam.note_v2.room;

import androidx.room.TypeConverter;

import java.io.File;

public class FileToStringConverter {

    @TypeConverter
    public String fromFileToString(File file)
    {
        return file.toString();
    }

    @TypeConverter
    public File fromStringToFile(String str)
    {
        return new File(str);
    }
}
