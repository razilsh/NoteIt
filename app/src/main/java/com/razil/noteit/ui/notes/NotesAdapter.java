package com.razil.noteit.ui.notes;

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
  private NotesAdapterItemClickHandler mItemClickHandler;

  NotesAdapter() {
  }

  public NotesAdapter(@NonNull List<NoteEntity> noteEntities) {
    mNoteEntities = noteEntities;
  }

  void setItemClickHandler(
      NotesAdapterItemClickHandler mItemClickHandler) {
    this.mItemClickHandler = mItemClickHandler;
  }

  void setNoteEntities(List<NoteEntity> noteEntities) {
    this.mNoteEntities = noteEntities;
    notifyDataSetChanged();
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    NoteEntity noteEntity = mNoteEntities.get(position);
    if (noteEntity.getTitle().isEmpty()) {
      holder.textTitle.setVisibility(View.GONE);
    } else {
      holder.textTitle.setVisibility(View.VISIBLE);
      holder.textTitle.setText(noteEntity.getTitle());
    }
    holder.textDescription.setText(noteEntity.getDescription());
  }

  @Override public int getItemCount() {
    return mNoteEntities != null ? mNoteEntities.size() : 0;
  }

  /**
   * Interface to handle click events on notes.
   */
  public interface NotesAdapterItemClickHandler {
    void onItemClick(int noteId);
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.text_description) TextView textDescription;

    ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      int noteId = mNoteEntities.get(adapterPosition).getId();
      mItemClickHandler.onItemClick(noteId);
    }
  }
}
