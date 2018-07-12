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
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.razil.noteit.R;
import com.razil.noteit.data.db.NoteEntity;
import com.razil.noteit.ui.SnackbarMessage;
import com.razil.noteit.util.InjectorUtils;
import java.util.List;

public class AddNoteFragment extends Fragment {
  static final ButterKnife.Action<View> DISABLE = (view, index) -> view.setEnabled(false);
  static final ButterKnife.Action<View> ENABLE = (view, index) -> view.setEnabled(true);

  private static final String TAG = "AddNoteFragment";
  Unbinder unbinder;
  @BindView(R.id.button_save_note) FloatingActionButton mSaveNoteButton;
  @BindView(R.id.text_title) EditText mTextTitle;
  @BindView(R.id.text_description) EditText mTextDescription;
  @BindView(R.id.button_edit_note) FloatingActionButton mEditNoteButton;
  @BindViews({ R.id.text_title, R.id.text_description })
  List<View> mTextViews;
  private NoteEntity mNoteEntity;

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

    mSaveNoteButton.animate().scaleX(1).scaleY(1).start();

    // TODO : Refactor into separate methods?

    AddNoteViewModelFactory factory =
        InjectorUtils.provideAddNoteViewModelFactory(requireContext());

    AddNoteViewModel viewModel = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);

    if (getArguments() != null) {
      Integer noteId = AddNoteFragmentArgs.fromBundle(getArguments()).getNoteId();
      if (noteId > 0) {
        viewModel.getNoteById(noteId).observe(this, noteEntity -> {
          if (noteEntity != null) {
            mNoteEntity = noteEntity;

            ButterKnife.apply(mTextViews, DISABLE);

            mTextTitle.setText(noteEntity.getTitle());
            mTextDescription.setText(noteEntity.getDescription());
            mSaveNoteButton.setVisibility(View.GONE);
            mEditNoteButton.setVisibility(View.VISIBLE);
            mEditNoteButton.animate().scaleX(1).scaleY(1).start();
          }
        });
      }
    }
    viewModel.getSnackbarMessage().observe(this,
        (SnackbarMessage.SnackbarObserver) snackbarMessageResourceId -> Snackbar.make(view,
            getString(snackbarMessageResourceId), Snackbar.LENGTH_LONG).show());

    viewModel.getNoteAdded()
        .observe(this, aVoid -> Navigation.findNavController(view).popBackStack());

    mSaveNoteButton.setOnClickListener(v -> {

      if (mNoteEntity != null) {
        mNoteEntity.setTitle(mTextTitle.getText().toString());
        mNoteEntity.setDescription(mTextDescription.getText().toString());
        viewModel.updateNote(mNoteEntity);
      } else {
        viewModel.addNote(mTextTitle.getText().toString(),
            mTextDescription.getText().toString());
      }
    });

    mEditNoteButton.setOnClickListener(v -> {
      ButterKnife.apply(mTextViews, ENABLE);
      mEditNoteButton.setVisibility(View.GONE);
      mSaveNoteButton.setVisibility(View.VISIBLE);
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mSaveNoteButton.animate().scaleX(0).scaleY(0).start();
    mEditNoteButton.animate().scaleX(0).scaleY(0).start();
    unbinder.unbind();
  }
}
