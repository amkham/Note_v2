package com.bam.note_v2.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bam.note_v2.R;
import com.bam.note_v2.edit.custom.spannable.SpanElementType;
import com.bam.note_v2.edit.custom.spannable.SpannableElement;
import com.bam.note_v2.room.NoteEntity;
import com.bam.note_v2.utils.ImageUtils;

import java.io.IOException;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<NoteEntity> mNoteList;

    private ItemOnClick itemOnClick;

    private Context context;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    interface ItemOnClick
    {
        void onclick(NoteEntity noteEntity, View view);
    }


    public NoteListAdapter(List<NoteEntity> mNoteList, Context context) {
        this.mNoteList = mNoteList;
        this.context = context;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.tTitle.setText(mNoteList.get(position).getTitle());
        holder.tData.setText(mNoteList.get(position).getDate());

        StringBuilder builder = new StringBuilder();

        for (SpannableElement e: mNoteList.get(position).getBody())
        {
            if (e.getType() == SpanElementType.TEXT)
            {
                builder.append(e.getText());
            }

            if (e.getType() == SpanElementType.IMAGE)
            {
                try {
                    Bitmap bitmap = ImageUtils.loadImg(context.getApplicationContext(), e.getValue());
                    holder.imageView.setImageBitmap(bitmap);
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        }

        holder.tBody.setText(builder);

        holder.cardView.setOnClickListener(v -> itemOnClick.onclick(mNoteList.get(position), v));



    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tTitle, tBody, tData;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.note_list_card_view);
            tTitle = itemView.findViewById(R.id.title);
            tBody = itemView.findViewById(R.id.body);
            tData = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.item_img);



        }
    }



}
