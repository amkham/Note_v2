package com.bam.note_v2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepostFragment extends Fragment {

    private ActivityResultLauncher<Intent> __getContactSelectCallBack;
    private ActivityResultLauncher<Intent> __messageCallBack;
    private ActivityResultLauncher<String> __permissionsCallBack;

    private Button __btn;
    private EditText __editText;


    public RepostFragment() {
        // Required empty public constructor
    }

    public static RepostFragment newInstance(String param1, String param2) {
        return new RepostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_repost, container, false);

        __btn = view.findViewById(R.id.button);
        __editText = view.findViewById(R.id.textView);


        __getContactSelectCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            assert result.getData() != null;

            String number = result.getData().getStringExtra("contact");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + number + "&text=" + __editText.getText().toString()));

            //startActivity(Intent.createChooser(intent, "Finish: "));
            __messageCallBack.launch(Intent.createChooser(intent, "Finish: "));
        });

        __messageCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            System.out.println();
        });


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setPackage("com.whatsapp");

        __btn.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                    PackageManager.PERMISSION_GRANTED) {

                //openWhatsApp(view);
                __getContactSelectCallBack.launch(intent);

            } else {
                __permissionsCallBack.launch(Manifest.permission.SEND_SMS);
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

        return view;
    }




}