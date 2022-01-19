package com.bam.note_v2.edit.presenter;

import android.net.Uri;

import java.io.FileNotFoundException;

public interface INoteEditPresenter {


    void onBolt();
    void onItalic();
    void onUnder();

    void changeTitle(String s);


    void save();

    void addImage(Uri uri, int start, String txt) throws FileNotFoundException;
}
