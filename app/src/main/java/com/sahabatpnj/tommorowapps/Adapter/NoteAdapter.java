package com.sahabatpnj.tommorowapps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahabatpnj.tommorowapps.Model.Note;
import com.sahabatpnj.tommorowapps.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mListNote = new ArrayList<>();
    private OnItemClick clickListener;


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = mListNote.get(position);
        holder.mTitle.setText(note.getTitle());
        holder.mContents.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return mListNote.size();
    }

    public void setmListNote(List<Note> mListNote) {
        notifyDataSetChanged();
        this.mListNote = mListNote;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mContents, mDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.textNoteItemTitle);
            mContents = itemView.findViewById(R.id.textNoteItemContens);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(mListNote.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClick {
        void onItemClick(Note note, int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.clickListener = onItemClick;
    }
}
