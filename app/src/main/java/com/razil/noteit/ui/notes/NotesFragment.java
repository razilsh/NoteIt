package com.razil.noteit.ui.notes;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.razil.noteit.R;
import com.razil.noteit.ui.notes.NotesFragmentDirections.AddNoteAction;
import com.razil.noteit.util.InjectorUtils;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/** Fragment that will display a list of all the notes and a button to add a note. */
public class NotesFragment extends Fragment {
  private static final String TAG = NotesFragment.class.getSimpleName();
  Unbinder unbinder;

  @BindView(R.id.button_add_note)
  FloatingActionButton mAddNoteButton;

  @BindView(R.id.recyclerView_notes)
  RecyclerView mRecyclerView;

  private NotesAdapter mNotesAdapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_notes, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @SuppressLint("RestrictedApi")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mAddNoteButton.animate().scaleX(1).scaleY(1).start();

    mNotesAdapter = new NotesAdapter();
    mNotesAdapter.setItemClickHandler(
        noteId -> {
          AddNoteAction action = NotesFragmentDirections.addNoteAction();
          action.setNoteId(noteId);
          Navigation.findNavController(view).navigate(action);
        });

    mRecyclerView.setAdapter(mNotesAdapter);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

    NotesViewModelFactory factory = InjectorUtils.provideNotesViewModelFactory(requireActivity());
    NotesViewModel viewModel =
        ViewModelProviders.of(requireActivity(), factory).get(NotesViewModel.class);
    viewModel.getAllNotes().observe(this, mNotesAdapter::setNoteEntities);

    mAddNoteButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addNoteAction));
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_home, menu);
    FragmentActivity activity = requireActivity();
    SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    if (searchView != null && searchManager != null) {
      searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
      searchView.setOnQueryTextListener(
          new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
              mNotesAdapter.getFilter().filter(s);
              return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
              mNotesAdapter.getFilter().filter(s);
              return false;
            }
          });
    }

    MenuItem sortItem = menu.findItem(R.id.action_sort);
    inflater.inflate(R.menu.sub_menu_sort, sortItem.getSubMenu());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_search) {
      return true;
    }

    if (item.getItemId() == R.id.action_sort_newest) {
      mNotesAdapter.sortNotes(false);
    }

    if (item.getItemId() == R.id.action_sort_oldest) {
      Toast.makeText(requireContext(), "oldest", Toast.LENGTH_SHORT).show();
      mNotesAdapter.sortNotes(true);
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mAddNoteButton.animate().scaleX(0).scaleY(0).start();
    unbinder.unbind();
  }
}
