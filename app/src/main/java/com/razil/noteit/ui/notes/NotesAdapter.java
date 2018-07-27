package com.razil.noteit.ui.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.razil.noteit.R;
import com.razil.noteit.data.db.NoteEntity;
import com.razil.noteit.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>
    implements Filterable {
  private static final String TAG = NotesAdapter.class.getSimpleName();
  private List<NoteEntity> mNoteEntities;
  private List<NoteEntity> mFilteredNoteEntities;
  private NotesAdapterItemClickHandler mItemClickHandler;

  NotesAdapter() {}

  public NotesAdapter(@NonNull List<NoteEntity> noteEntities) {
    mNoteEntities = noteEntities;
    mFilteredNoteEntities = noteEntities;
  }

  void setItemClickHandler(NotesAdapterItemClickHandler mItemClickHandler) {
    this.mItemClickHandler = mItemClickHandler;
  }

  void setNoteEntities(List<NoteEntity> noteEntities) {
    this.mNoteEntities = noteEntities;
    this.mFilteredNoteEntities = noteEntities;
    notifyDataSetChanged();
  }

  void sortNotes(boolean desc) {
    Collections.sort(
        mFilteredNoteEntities,
        (noteEntity, t1) ->
            desc
                ? noteEntity.getCreatedAt().compareTo(t1.getCreatedAt())
                : t1.getCreatedAt().compareTo(noteEntity.getCreatedAt()));

    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    NoteEntity noteEntity = mFilteredNoteEntities.get(position);
    if (noteEntity.getTitle().isEmpty()) {
      holder.textTitle.setVisibility(View.GONE);
    } else {
      holder.textTitle.setVisibility(View.VISIBLE);
      holder.textTitle.setText(noteEntity.getTitle());
    }
    holder.textDescription.setText(noteEntity.getDescription());
  }

  @Override
  public int getItemCount() {
    return mFilteredNoteEntities != null ? mFilteredNoteEntities.size() : 0;
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String filterString = charSequence.toString();
        if (Validator.isNullOrEmpty(filterString)) {
          mFilteredNoteEntities = mNoteEntities;
        } else {
          List<NoteEntity> noteEntities = new ArrayList<>();
          for (NoteEntity noteEntity : mNoteEntities) {
            if (noteEntity.getTitle().contains(filterString)
                || noteEntity.getDescription().contains(filterString)) {
              noteEntities.add(noteEntity);
            }
          }
          mFilteredNoteEntities = noteEntities;
        }
        FilterResults results = new FilterResults();
        results.values = mFilteredNoteEntities;
        return results;
      }

      @Override
      protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mFilteredNoteEntities = (List<NoteEntity>) filterResults.values;
        for (NoteEntity noteEntity : mFilteredNoteEntities) {
          Log.d(TAG, "publishResults: noteEntity = " + noteEntity.getTitle());
        }
        notifyDataSetChanged();
      }
    };
  }

  /** Interface to handle click events on notes. */
  public interface NotesAdapterItemClickHandler {
    void onItemClick(int noteId);
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.text_title)
    TextView textTitle;

    @BindView(R.id.text_description)
    TextView textDescription;

    ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      int noteId = mNoteEntities.get(adapterPosition).getId();
      mItemClickHandler.onItemClick(noteId);
    }
  }
}
