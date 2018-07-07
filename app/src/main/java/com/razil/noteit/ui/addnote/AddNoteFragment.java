package com.razil.noteit.ui.addnote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.razil.noteit.R;
import com.razil.noteit.ui.SnackbarMessage;
import com.razil.noteit.util.InjectorUtils;

public class AddNoteFragment extends Fragment {
  private static final String TAG = "AddNoteFragment";
  Unbinder unbinder;

  @BindView(R.id.button_save_note) FloatingActionButton mSaveNoteButton;
  @BindView(R.id.text_title) EditText mTextTitle;
  @BindView(R.id.text_description) EditText mTextDescription;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_add_note, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @SuppressLint("RestrictedApi") @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // TODO : Refactor into separate methods?

    AddNoteViewModelFactory factory =
        InjectorUtils.provideAddNoteViewModelFactory(requireContext());

    AddNoteViewModel viewModel = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);

    if (getArguments() != null) {
      Integer noteId = AddNoteFragmentArgs.fromBundle(getArguments()).getNoteId();
      if (noteId > 0) {
        viewModel.getNoteById(noteId).observe(this, noteEntity -> {
          if (noteEntity != null) {

            mTextTitle.setText(noteEntity.getTitle());
            mTextTitle.setEnabled(false);

            mTextDescription.setText(noteEntity.getDescription());
            mTextDescription.setEnabled(false);

            mSaveNoteButton.setVisibility(View.GONE);
          }
        });
      }
    }
    viewModel.getSnackbarMessage().observe(this,
        (SnackbarMessage.SnackbarObserver) snackbarMessageResourceId -> Snackbar.make(view,
            getString(snackbarMessageResourceId), Snackbar.LENGTH_LONG).show());

    viewModel.getNoteAdded()
        .observe(this, aVoid -> Navigation.findNavController(view).popBackStack());

    mSaveNoteButton.setOnClickListener(v -> viewModel.addNote(mTextTitle.getText().toString(),
        mTextDescription.getText().toString()));
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
