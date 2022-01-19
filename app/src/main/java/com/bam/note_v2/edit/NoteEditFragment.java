package com.bam.note_v2.edit;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bam.note_v2.R;
import com.bam.note_v2.edit.custom.NoteView;
import com.bam.note_v2.edit.presenter.NoteEditPresenter;
import com.bam.note_v2.room.NoteEntity;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.io.FileNotFoundException;

public class NoteEditFragment extends Fragment {

    private NoteEntity note;

    private EditText title, body;
    private BottomAppBar bottomAppBar;
    private NoteEditPresenter presenter;

    private NoteView noteView;

    private boolean mChangeStatus;

    private View view;

    private ActivityResultLauncher<String> mGetContent;

    public NoteEditFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            note = (NoteEntity) getArguments().getSerializable("note");
        }

        presenter = new NoteEditPresenter(this);




       mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {

                    try {
                        presenter.addImage(uri, noteView.getSelectionStart(), noteView.getStylizedText());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_edit, container, false);

        noteView = new NoteView(this.requireContext());
        addView(noteView);


        title = view.findViewById(R.id.et_title);
        bottomAppBar = view.findViewById(R.id.bottomAppBar);

        if (note != null) {
            title.setText(note.getTitle());
            body.setText(note.getBody());
        }


        bottomAppBar.setNavigationOnClickListener(v ->
        {
            mGetContent.launch("image/*");

        });


        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.bolt):
                    noteView.onBolt();
                    break;
                case (R.id.italic):
                    noteView.onItalic();
                    break;
                case (R.id.under):
                    noteView.onUnder();
                    break;
            }
            return false;
        });


        return view;
    }


    public void setImage(SpannableStringBuilder txt)
    {
        noteView.setText(txt);
    }

    public void setTitle(SpannableStringBuilder txt)
    {
        title.setText(txt);
    }

    public void addView(View v)
    {
        LinearLayout linearLayout = view.findViewById(R.id.note_edittext_container);
        linearLayout.addView(v);
    }



    @Override
    public void onStop() {
        super.onStop();
        presenter.save();
    }
}