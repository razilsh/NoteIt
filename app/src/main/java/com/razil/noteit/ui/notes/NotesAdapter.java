package com.razil.noteit.ui.notes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.razil.noteit.R;
import com.razil.noteit.data.db.NoteEntity;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
  private List<NoteEntity> mNoteEntities;
  private Context mContext;

  public NotesAdapter() {
  }

  public NotesAdapter(@NonNull Context context, @NonNull List<NoteEntity> noteEntities) {
    mContext = context;
    mNoteEntities = noteEntities;
  }

  public void setNoteEntities(List<NoteEntity> noteEntities) {
    this.mNoteEntities = noteEntities;
    notifyDataSetChanged();
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    NoteEntity noteEntity = mNoteEntities.get(position);
    holder.textTitle.setText(noteEntity.getTitle());
    holder.textDescription.setText(noteEntity.getDescription());
  }

  @Override public int getItemCount() {
    return mNoteEntities != null ? mNoteEntities.size() : 0;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.text_description) TextView textDescription;

    ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
