package com.bam.note_v2.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bam.note_v2.R;
import com.bam.note_v2.main.presenter.MainFragmentPresenter;
import com.bam.note_v2.room.LocalDataBase;
import com.bam.note_v2.room.NoteDao;
import com.bam.note_v2.room.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class NoteListFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView noteRecyclerView;


    private final MainFragmentPresenter presenter;


    public NoteListFragment() {

        presenter = new MainFragmentPresenter(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        noteRecyclerView = view.findViewById(R.id.main_recyclerview);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fb_create_note);
        floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.noteEditFragment));

        presenter.getAllNote();

        return view;
    }


    public void createNoteList(List<NoteEntity> entities)
    {

        NoteListAdapter listAdapter = new NoteListAdapter(entities);

        listAdapter.setItemOnClick((position, view) -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("note", entities.get(position));
            Navigation.findNavController(view).navigate(R.id.noteEditFragment, bundle);
        });
        noteRecyclerView.setAdapter(listAdapter);

    }




}