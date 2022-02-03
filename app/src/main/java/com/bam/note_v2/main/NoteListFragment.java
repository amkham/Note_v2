package com.bam.note_v2.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bam.note_v2.R;
import com.bam.note_v2.main.presenter.IMainFragmentContract;
import com.bam.note_v2.main.presenter.MainFragmentPresenter;
import com.bam.note_v2.room.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListFragment extends Fragment implements IMainFragmentContract.View {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView noteRecyclerView;
    private NoteListAdapter listAdapter;


    private IMainFragmentContract.Presenter __presenter;


    public NoteListFragment() {


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

        __presenter = new MainFragmentPresenter(this);
        __presenter.loadNotes();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fb_create_note);

        floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.noteEditFragment);
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void createList(List<NoteEntity> entities) {
        listAdapter = new NoteListAdapter(entities, requireContext());

        listAdapter.setItemOnClick((note, view) -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("note", note);
            Navigation.findNavController(view).navigate(R.id.noteEditFragment, bundle);
        });
        noteRecyclerView.setAdapter(listAdapter);
    }
}