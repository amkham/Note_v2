package com.bam.note_v2.edit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ListPopupWindowCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bam.note_v2.MainActivity;
import com.bam.note_v2.R;
import com.bam.note_v2.edit.customview.INoteBodyElement;
import com.bam.note_v2.edit.customview.NoteImageView;
import com.bam.note_v2.edit.customview.NoteTextView;
import com.bam.note_v2.edit.customview.spannable.SpanElementType;
import com.bam.note_v2.edit.customview.spannable.SpannableChar;
import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.edit.customview.spannable.SpannableImage;
import com.bam.note_v2.edit.customview.spannable.TextStyle;
import com.bam.note_v2.utils.ImageUtils;
import com.bam.note_v2.viewmodel.IViewModelContract;
import com.bam.note_v2.viewmodel.NoteViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteEditFragment extends Fragment implements IStyledCharListener {


    private View __view;
    private EditText __title;

    private LinearLayout __bodyContainer;

    private List<IObserver.StyleObserver> __charFormattingObservers;

    private ActivityResultLauncher<String> __getImageFromStorageCallBack;

    private ActivityResultLauncher<String> __permissionsCallBack;

    private IViewModelContract.NoteEditFragment __viewModel;


    private LinearLayout __paletteContainer;

    private String TAG = "LOG";

    boolean __onBolt, __onUnder, __onItalic;

    private float __textSize;


    public NoteEditFragment() {

        System.out.println();
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
        __bodyContainer = __view.findViewById(R.id.note_body);
        __title = __view.findViewById(R.id.note_title);

        __title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                __viewModel.setTitle(s.toString());
            }
        });



        __viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);


        try {
            __viewModel.getSelectedNote().observe(getViewLifecycleOwner(), noteEntity -> {
                __title.setText(noteEntity.getTitle());
                try {
                    createNoteStructure(requireContext(),noteEntity.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        __viewModel.getNoteBodyStructure().observe(getViewLifecycleOwner(), this::fillBody);

        initPalette(__view);

        initBottomAppBar(__view);


        return __view;
    }




    private void fillBody(List<INoteBodyElement> elements) {

        if (__bodyContainer != null)
        {
            __bodyContainer.removeAllViewsInLayout();
            __bodyContainer.removeAllViews();
        }


        for (INoteBodyElement e : elements) {


            if (e.getContent().size() > 0 && e.getContent().get(0).getType() == SpanElementType.IMAGE)
            {

                __bodyContainer.addView(createNoteBodyImageContainer((NoteImageView) e));

            }

            if (e.getContent().size() == 0 || e.getContent().get(0).getType() == SpanElementType.TEXT)
            {
                __bodyContainer.addView((View) e);
            }


        }
    }

    @NonNull
    private RelativeLayout createNoteBodyImageContainer(@NonNull NoteImageView imageView)
    {
        if (imageView.getParent() != null)
        {
            ((ViewGroup)imageView.getParent()).removeView(imageView);
        }

        RelativeLayout _relativeLayout = new RelativeLayout(requireContext());
        ViewGroup.LayoutParams _layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        _relativeLayout.setLayoutParams(_layoutParams);

        ImageView _deleteBtn = createNoteBodyImageDeleteBtn(imageView);


        _relativeLayout.addView(imageView);
        _relativeLayout.addView(_deleteBtn);

        return _relativeLayout;
    }

    @NonNull
    private ImageView createNoteBodyImageDeleteBtn(NoteImageView noteImageView)
    {
        ImageView _imageView = new ImageView(requireContext());

        RelativeLayout.LayoutParams _deleteImgParams = new RelativeLayout.LayoutParams(150,150);
        _deleteImgParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        _imageView.setLayoutParams(_deleteImgParams);

        _imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delite));
        _imageView.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Удалить изображение?")
                    .setPositiveButton(getResources().getString(R.string.positive), (dialog, which) -> {

                        deleteView(noteImageView);

                    })
                    .setNegativeButton(getResources().getString(R.string.negative), (dialog, which) -> {

                    }).show();
        });

        return _imageView;

    }

    private void createNoteStructure(Context context, @NonNull List<SpannableElement> spannableElements) throws IOException {

        __viewModel.resetPosition();

        __charFormattingObservers = new ArrayList<>();
        List<SpannableElement> _buffer = new ArrayList<>();

        List<INoteBodyElement> _noteBodyStructure = new ArrayList<>();


        if (spannableElements.size() == 0) {
            NoteTextView _noteTextView = createEditText(context, new ArrayList<>());
            _noteBodyStructure.add(_noteTextView);

        }

        for (SpannableElement s : spannableElements) {

            if (s.getType() == SpanElementType.TEXT) {
                _buffer.add(s);
            }

            if (s.getType() == SpanElementType.IMAGE) {
                NoteTextView _noteTextView = createEditText(context, new ArrayList<>(_buffer));

                NoteImageView _imageView = createImageView(context, s);

                _noteBodyStructure.add(_noteTextView);
                _noteBodyStructure.add(_imageView);
                _buffer.clear();
            }
        }


        if (_buffer.size() > 0) {
            NoteTextView _noteTextView = createEditText(context, new ArrayList<>(_buffer));
            _noteBodyStructure.add(_noteTextView);
        }


        int _lastIndex = spannableElements.size() - 1;

        if (spannableElements.size() > 0)
        {
            if (spannableElements.get(_lastIndex).getType() == SpanElementType.IMAGE)
            {
                NoteTextView _noteTextView = createEditText(context, new ArrayList<>(_buffer));
                _noteBodyStructure.add(_noteTextView);
            }

        }

        __viewModel.getNoteBodyStructure().setValue(new ArrayList<>(_noteBodyStructure));

    }


    @NonNull
    private NoteImageView createImageView(Context context, SpannableElement element) throws IOException {

        NoteImageView _noteImageView = new NoteImageView(context);
        _noteImageView.setPosition(__viewModel.getPosition());

        _noteImageView.setSpannableImage(element);

        return _noteImageView;
    }

    private void deleteView(@NonNull INoteBodyElement e)
    {
        List<INoteBodyElement> _noteBodyElements = __viewModel.getNoteBodyStructure().getValue();
        int _index = _noteBodyElements.indexOf(e);


        List<SpannableElement> _afterImageElements = _noteBodyElements.get(_index + 1).getContent();
        List<SpannableElement>  _beforeImageElements =  _noteBodyElements.get(_index -1).getContent();
        _beforeImageElements.add(new SpannableChar("\n", new TextStyle()));
        _beforeImageElements.addAll(_afterImageElements);

        _noteBodyElements.remove(_index + 1);
        _noteBodyElements.remove(_index);
        _noteBodyElements.remove(_index - 1);

        NoteTextView _noteTextView = new NoteTextView(requireContext());
        _noteTextView.setContent(_beforeImageElements);

        _noteBodyElements.add(_index -1, _noteTextView);

        for (INoteBodyElement i: _noteBodyElements)
        {

        }

        __viewModel.getNoteBodyStructure().setValue(_noteBodyElements);

    }

    private NoteTextView createEditText(Context context, List<SpannableElement> elements) {

        NoteTextView _noteTextView = new NoteTextView(context);
        int _position = __viewModel.getPosition();

        if (_position == 0)
        {
            _noteTextView.setHint(Html.fromHtml("<i>" + getResources().getString(R.string.note_body_hint) + "</i>"));
        }
        _noteTextView.setPosition(_position);

        if (__onBolt) _noteTextView.onBolt();
        if (__onUnder) _noteTextView.onUnder();
        if(__onItalic) _noteTextView.onItalic();

        register(_noteTextView);
        _noteTextView.setContent(elements);

        return _noteTextView;
    }


    private void initCallBacks() {
        __getImageFromStorageCallBack = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            String _filename = "";
            if (result != null)
            {
                try {
                    Bitmap bitmap = ImageUtils.crateBitmapFromUri(requireContext(), result);
                    _filename = ImageUtils.saveImg(requireContext(), bitmap);

                    SpannableImage _image = new SpannableImage(_filename);
                    NoteImageView _imageView = createImageView(getContext(), _image);
                    NoteTextView _textView = createEditText(getContext(), new ArrayList<>());


                    List<INoteBodyElement> _elements = __viewModel.getNoteBodyStructure().getValue();

                    _elements.add(_imageView);
                    _elements.add(_textView);

                    __viewModel.getNoteBodyStructure().setValue(new ArrayList<>(_elements));

                } catch (IOException e) {
                    e.printStackTrace();
                }
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


    private void initPalette(View v) {
        CardView _transparent = v.findViewById(R.id.empty);
        CardView _red = v.findViewById(R.id.red);
        CardView _blue = v.findViewById(R.id.blue);
        CardView _orange = v.findViewById(R.id.orange);
        CardView _green = v.findViewById(R.id.green);
        CardView _purpur = v.findViewById(R.id.purpur);

        ImageView _palette_btn = v.findViewById(R.id.paint_btn);

        __paletteContainer = v.findViewById(R.id.palette_container);
        __paletteContainer.setVisibility(View.INVISIBLE);

        _palette_btn.setOnClickListener(view ->
        {
            __paletteContainer.setVisibility(View.VISIBLE);
        });

        _transparent.setOnClickListener(view -> {
            notifyObservers(IObserver.Colors.TRANSPARENT);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });
        _red.setOnClickListener(view -> {
            notifyObservers(IObserver.Colors.RED);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });

        _blue.setOnClickListener(view ->
        {
            notifyObservers(IObserver.Colors.BLUE);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });
        _orange.setOnClickListener(view -> {
            notifyObservers(IObserver.Colors.ORANGE);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });
        _green.setOnClickListener(view -> {
            notifyObservers(IObserver.Colors.GREEN);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });
        _purpur.setOnClickListener(view -> {
            notifyObservers(IObserver.Colors.PURPLE);
            __paletteContainer.setVisibility(View.INVISIBLE);
        });

    }


    private void initBottomAppBar(View view) {
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


        ImageView _bolt_btn = view.findViewById(R.id.bolt_btn);
        ImageView _under_btn = view.findViewById(R.id.under_btn);
        ImageView _italic_btn = view.findViewById(R.id.italic_btn);
        ImageView _size_btn = view.findViewById(R.id.size_btn);


        _bolt_btn.setOnClickListener(v -> {

            __onBolt = !__onBolt;
            notifyObservers(IObserver.Style.BOLT);
            changeBtnColor(v, __onBolt);
        });

        _under_btn.setOnClickListener(v -> {
            __onUnder = !__onUnder;
            notifyObservers(IObserver.Style.UNDER);
            changeBtnColor(v, __onUnder);
        });

        _italic_btn.setOnClickListener(v -> {
            __onItalic = !__onItalic;
            notifyObservers(IObserver.Style.ITALIC);
            changeBtnColor(v, __onItalic);
        });

        _size_btn.setOnClickListener(v -> showMenu(v, R.menu.menu_text_size));


    }

    private void showMenu(View v, int menuResource) {

        ListPopupWindow _listPopupWindow = new ListPopupWindow(requireContext());
        _listPopupWindow.setAnchorView(v);
        _listPopupWindow.setWidth(250);
        _listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        String[] _values = getResources().getStringArray(R.array.text_size);
        _listPopupWindow.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.text_size_menu_item, _values));
        _listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            __textSize = Float.parseFloat(_values[position]);
            notifyObservers(IObserver.Style.SIZE);
            _listPopupWindow.dismiss();
        });

        _listPopupWindow.show();
    }

    private void changeBtnColor(View v, boolean state) {
        TransitionDrawable _transitionDrawable = (TransitionDrawable) v.getBackground();
        if (state) {
            _transitionDrawable.startTransition(100);
        } else _transitionDrawable.reverseTransition(100);
    }


    /******************************************************************
     * Слушатели
     * @param o
     */

    @Override
    public void register(IObserver.StyleObserver o) {
        __charFormattingObservers.add(o);


    }



    @Override
    public void remove(IObserver.StyleObserver o) {
        __charFormattingObservers.remove(o);
    }

    @Override
    public void notifyObservers(IObserver.Style style) {

        switch (style) {
            case BOLT: {
                for (IObserver.StyleObserver o : __charFormattingObservers) {
                    o.onBolt();
                }
                break;
            }

            case ITALIC: {
                for (IObserver.StyleObserver o : __charFormattingObservers) {
                    o.onItalic();
                }
                break;
            }
            case UNDER: {
                for (IObserver.StyleObserver o : __charFormattingObservers) {
                    o.onUnder();
                }
                break;
            }

            case SIZE:
                for (IObserver.StyleObserver o : __charFormattingObservers) {
                    o.changeSize(__textSize);
                }
        }

    }

    @Override
    public void notifyObservers(IObserver.Colors color) {

        for (IObserver.StyleObserver o : __charFormattingObservers) {
            o.changeBackground(color.getValue());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "stop");
        __viewModel.saveNote();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "destroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "detach");
    }

}