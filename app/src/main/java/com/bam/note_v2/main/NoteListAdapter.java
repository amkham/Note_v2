package com.bam.note_v2.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bam.note_v2.R;
import com.bam.note_v2.room.NoteEntity;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<NoteEntity> mNoteList;

    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    interface ItemOnClick
    {
        void onclick(int position, View view);
    }


    public NoteListAdapter(List<NoteEntity> mNoteList) {
        this.mNoteList = mNoteList;
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
        holder.tBody.setText(mNoteList.get(position).getBody());

        holder.cardView.setOnClickListener(v -> itemOnClick.onclick(position, v));


    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tTitle, tBody, tData;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.note_list_card_view);
            tTitle = itemView.findViewById(R.id.title);
            tBody = itemView.findViewById(R.id.body);
            tData = itemView.findViewById(R.id.date);

        }
    }
}
