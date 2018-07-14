package com.razil.noteit.ui.addnote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import com.razil.noteit.R;

public class DeleteNoteDialogFragment extends DialogFragment {
  private int mNoteId;

  public interface UserActionListener {
    void onActionPerformed(int action);
  }

  public static DeleteNoteDialogFragment newInstance() {
    return new DeleteNoteDialogFragment();
  }

  @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new AlertDialog.Builder(requireContext()).setTitle("Confirm delete")
        .setMessage(R.string.confirm_delete_note)
        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
          UserActionListener listener = (UserActionListener) getTargetFragment();
          if (listener != null) {
            listener.onActionPerformed(i);
            dismiss();
          }
        }).setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
          UserActionListener listener = (UserActionListener) getTargetFragment();
          if (listener != null) {
            listener.onActionPerformed(i);
            dismiss();
          }
        })
        .create();
  }
}
