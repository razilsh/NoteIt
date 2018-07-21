package com.razil.noteit.ui.deletenote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.razil.noteit.R;

public class DeleteNoteDialogFragment extends DialogFragment {
  private UserActionListener mUserActionListener;

  public interface UserActionListener {
    void onActionPerformed(int action);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      mUserActionListener = (UserActionListener) getTargetFragment();
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must implement UserActionListener");
    }
  }

  public static DeleteNoteDialogFragment newInstance() {
    return new DeleteNoteDialogFragment();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new AlertDialog.Builder(requireContext())
        .setTitle("Delete note?")
        .setMessage(R.string.confirm_delete_note)
        .setPositiveButton(
            R.string.delete,
            (dialogInterface, i) -> {
              mUserActionListener.onActionPerformed(i);
              dismiss();
            })
        .setNegativeButton(
            android.R.string.cancel,
            (dialogInterface, i) -> {
              mUserActionListener.onActionPerformed(i);
              dismiss();
            })
        .create();
  }
}
