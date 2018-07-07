package com.razil.noteit.ui.notes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.razil.noteit.R;
import com.razil.noteit.data.db.NoteEntity;
import com.razil.noteit.util.InjectorUtils;

/**
 * Fragment that will display a list of all the notes with a button to add a note.
 */
public class NotesFragment extends Fragment {
  private static final String TAG = NotesFragment.class.getSimpleName();
  Unbinder unbinder;
  @BindView(R.id.button_add_note) FloatingActionButton mAddNoteButton;
  @BindView(R.id.recyclerView_notes) RecyclerView mRecyclerView;
  
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_notes, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    NotesAdapter notesAdapter = new NotesAdapter();
    mRecyclerView.setAdapter(notesAdapter);

    NotesViewModelFactory factory = InjectorUtils.provideNotesViewModelFactory(requireActivity());

    NotesViewModel viewModel =
        ViewModelProviders.of(requireActivity(), factory).get(NotesViewModel.class);

    viewModel.getAllNotes().observe(this, noteEntities -> {
      if (noteEntities != null) {
        for (NoteEntity noteEntity : noteEntities) {
          Log.d(TAG, "Note Creation date: " + noteEntity.getCreatedAt());
        }
      }
      notesAdapter.setNoteEntities(noteEntities);
    });

    mAddNoteButton.setOnClickListener(
        Navigation.createNavigateOnClickListener(
            R.id.action_notesFragment_to_addNoteFragment));
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
