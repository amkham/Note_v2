package com.bam.note_v2.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bam.note_v2.R;
import com.bam.note_v2.main.adapter.NoteListAdapter;
import com.bam.note_v2.main.adapter.TouchHelperCallback;
import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.viewmodel.IViewModelContract;
import com.bam.note_v2.viewmodel.NoteViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NoteListFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView noteRecyclerView;
    private NoteListAdapter listAdapter;

    private IViewModelContract.NoteListFragment __viewModel;


    private ActivityResultLauncher<Intent> __getContactSelectCallBack;
    private ActivityResultLauncher<Intent> __messageCallBack;
    private ActivityResultLauncher<String> __permissionsCallBack;


    private LinearLayout _repostSelectLinear;
    private View __view;


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

        __view = inflater.inflate(R.layout.fragment_note_list, container, false);




        noteRecyclerView = __view.findViewById(R.id.main_recyclerview);


        __viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);


        try {
            __viewModel.getAllNotes().observe(getViewLifecycleOwner(), entities -> {

                List<NoteEntity> _noteEntityList = entities.stream().sorted().collect(Collectors.toList());
                createList(_noteEntityList);

            });
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        ImageView addNoteBtn = __view.findViewById(R.id.note_add_btn);

        addNoteBtn.setOnClickListener(v -> {

            try {
                __viewModel.createNote();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            Navigation.findNavController(__view).navigate(R.id.noteEditFragment);
        });


        initRepostBlock(__view);

        return __view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {

        super.onResume();
    }


    private void createList(List<NoteEntity> entities) {


        listAdapter = new NoteListAdapter(entities, requireContext());

        listAdapter.setItemOnClick(note -> {


            Navigation.findNavController(__view).navigate(R.id.noteEditFragment);
            __viewModel.editNote(note);
        });
        noteRecyclerView.setAdapter(listAdapter);


        ItemTouchHelper.Callback _callback = new TouchHelperCallback(position -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Удалить заметку?")
                    .setPositiveButton(getResources().getString(R.string.positive), (dialog, which) -> {

                        __viewModel.deleteNote(listAdapter.getNote(position));
                    })
                    .setNegativeButton(getResources().getString(R.string.negative), (dialog, which) -> {

                        listAdapter.notifyItemChanged(position);
                    }).show();
            return null;
        });
        ItemTouchHelper _itemTouchHelper = new ItemTouchHelper(_callback);
        _itemTouchHelper.attachToRecyclerView(noteRecyclerView);
    }


    private void initRepostBlock(View v) {
        initRepostStructure(v);
        initRepostItems(v);
        initPermissionCallBack();
        initWhatsAppCallBack();

    }

    private void initWhatsAppCallBack() {

        __getContactSelectCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getData() != null)
            {
                String _msg = "Repost, pls!";

                String number = result.getData().getStringExtra("contact");

                if (number != null)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + number + "&text=" + _msg));
                    __messageCallBack.launch(Intent.createChooser(intent, "Finish: "));
                }

            }

        });


        __messageCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            saveRepostInSharedPreference(_repostSelectLinear);
            changeRepostViewStatus(_repostSelectLinear);
        });
    }

    private void initPermissionCallBack() {

        __permissionsCallBack = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {

                    if (isGranted) {
                        Toast.makeText(requireContext(), "granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "denied", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initRepostStructure(View v) {
        ImageView _header = v.findViewById(R.id.repost_header);
        LinearLayout _body = v.findViewById(R.id.repost_body_container);
        _body.setVisibility(View.INVISIBLE);

        _header.setOnClickListener(v1 -> {

            if (_body.getVisibility() == View.VISIBLE) {
                _body.setVisibility(View.INVISIBLE);
            } else {
                _body.setVisibility(View.VISIBLE);
            }

        });
    }

    private void initRepostItems(View v) {

        int count = 0;

        LinearLayout _repostItem1 = v.findViewById(R.id.repost_item1);

        if (checkRepostStatus(_repostItem1))
        {
            changeRepostViewStatus(_repostItem1);
            count++;
        }

        LinearLayout _repostItem2 = v.findViewById(R.id.repost_item2);
        if (checkRepostStatus(_repostItem2))
        {
            changeRepostViewStatus(_repostItem2);
            count++;
        }

        LinearLayout _repostItem3 = v.findViewById(R.id.repost_item3);
        if (checkRepostStatus(_repostItem3))
        {
            changeRepostViewStatus(_repostItem3);
            count++;
        }

        LinearLayout _repostItem4 = v.findViewById(R.id.repost_item4);
        if (checkRepostStatus(_repostItem4))
        {
            changeRepostViewStatus(_repostItem4);
            count++;
        }

        LinearLayout _repostItem5 = v.findViewById(R.id.repost_item5);
        if (checkRepostStatus(_repostItem5))
        {
            changeRepostViewStatus(_repostItem5);
            count++;
        }


        if (count == 5)
        {
            SharedPreferences _sharedPreferences = getActivity().getSharedPreferences("repost", Context.MODE_PRIVATE);
            SharedPreferences.Editor _editor = _sharedPreferences.edit();
            _editor.putBoolean("status", true);
            _editor.apply();

            noteRecyclerView.setPadding(0,0,0,0);
            RelativeLayout _relativeLayout = v.findViewById(R.id.repost_container);
            _relativeLayout.setVisibility(View.INVISIBLE);
        }
        else
        {
            View.OnClickListener _onClickListener = v1 -> {



                    try {
                        Objects.requireNonNull(getActivity()).getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setPackage("com.whatsapp");
                        __getContactSelectCallBack.launch(intent);

                        _repostSelectLinear = (LinearLayout) v1;

                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Установите WhatsApp", Toast.LENGTH_SHORT).show();
                    }

            };

            _repostItem1.setOnClickListener(_onClickListener);
            _repostItem2.setOnClickListener(_onClickListener);
            _repostItem3.setOnClickListener(_onClickListener);
            _repostItem4.setOnClickListener(_onClickListener);
            _repostItem5.setOnClickListener(_onClickListener);
        }

    }

    private void saveRepostInSharedPreference(LinearLayout layout)
    {
        SharedPreferences _sharedPreferences = requireContext().getSharedPreferences("repost", Context.MODE_PRIVATE);
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putBoolean(String.valueOf(layout.getId()), true);
        _editor.apply();
    }





    private boolean checkRepostStatus(LinearLayout linearLayout)
    {
        SharedPreferences _sharedPreferences = requireContext().getSharedPreferences("repost", Context.MODE_PRIVATE);
        return  _sharedPreferences.getBoolean(String.valueOf(linearLayout.getId()), false);


    }

    private void changeRepostViewStatus(LinearLayout linearLayout)
    {
        linearLayout.setOnClickListener(null);
        ImageView _repostStatusIc = (ImageView) linearLayout.getChildAt(0);
        TextView _repostSuccessText = (TextView) linearLayout.getChildAt(1);
        TextView _repostSendText = (TextView) linearLayout.getChildAt(2);
        ImageView _whatsAppIc = (ImageView) linearLayout.getChildAt(3);

        _repostStatusIc.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
        _repostSuccessText.setText("репост отправлен");
        _repostSendText.setText("");
        _whatsAppIc.setVisibility(View.INVISIBLE);
    }


}