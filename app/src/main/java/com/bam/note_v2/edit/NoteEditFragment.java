package com.bam.note_v2.edit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bam.note_v2.R;
import com.bam.note_v2.edit.custom.INoteBodyElement;
import com.bam.note_v2.edit.custom.NoteImageView;
import com.bam.note_v2.edit.custom.NoteTextView;
import com.bam.note_v2.edit.custom.spannable.SpannableImage;
import com.bam.note_v2.edit.presenter.INoteEditFragmentContract;
import com.bam.note_v2.edit.presenter.NoteEditPresenter;
import com.bam.note_v2.utils.ImageUtils;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.io.IOException;
import java.util.List;

public class NoteEditFragment extends Fragment implements INoteEditFragmentContract.EditFragment {


    private View __view;
    private EditText __title;
    private NoteEditPresenter __presenter;

    private LinearLayout __body;


    private ActivityResultLauncher<String> __getImageFromStorageCallBack;

    private ActivityResultLauncher<String> __permissionsCallBack;




    public NoteEditFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCallBacks();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        __view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        __body = __view.findViewById(R.id.note_body);
        __title = __view.findViewById(R.id.note_title);
        __presenter = new NoteEditPresenter(this);

        initBottomAppBar(__view);



        return __view;
    }



    @Override
    public void onStop() {
        super.onStop();

    }


    private void initCallBacks()
    {
        __getImageFromStorageCallBack = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            String filename ="";
            try {
                Bitmap bitmap = ImageUtils.crateBitmapFromUri(requireContext(),result);
                filename = ImageUtils.saveImg(requireContext(), bitmap, "resadf");
            } catch (IOException e) {
                e.printStackTrace();
            }



        });


        __permissionsCallBack = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {

                    if (isGranted) {
                        Toast.makeText(requireContext(), "granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "denied", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void initBottomAppBar(View view)
    {
        BottomAppBar bottomAppBar = view.findViewById(R.id.bottomAppBar);


        bottomAppBar.setNavigationOnClickListener(v ->
        {

            if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {

                __getImageFromStorageCallBack.launch("image/*");

            } else {
                __permissionsCallBack.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                __permissionsCallBack.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

        });


//        bottomAppBar.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case (R.id.bolt):
//                    __body.onBolt();
//                    break;
//                case (R.id.italic):
//                    __body.onItalic();
//                    break;
//                case (R.id.under):
//                    __body.onUnder();
//                    break;
//            }
//            return false;
//        });
    }




    private NoteImageView initNoteImageView(SpannableImage image) throws IOException {

        NoteImageView _noteImageView = new NoteImageView(requireContext());
        _noteImageView.setSpannableImage(image);

        return  _noteImageView;
    }


    private NoteTextView initNoteTextView()
    {
        return  new NoteTextView(requireContext());
    }


    @Override
    public void setTitle(String txt) {

    }

    @Override
    public void setBody(List<INoteBodyElement> elements) throws IOException {

    }

    @Override
    public Context getContext(Context context) {
        return null;
    }
}