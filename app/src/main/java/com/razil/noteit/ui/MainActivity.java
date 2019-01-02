package com.razil.noteit.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.razil.noteit.R;

public class MainActivity extends AppCompatActivity {

  Toolbar toolbar;
  NavigationView navView;
  DrawerLayout drawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = findViewById(R.id.toolbar);
    navView = findViewById(R.id.nav_view);
    drawerLayout = findViewById(R.id.drawer_layout);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("Notes");
    }

    NavHostFragment navHost =
        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

    NavController navController = navHost.getNavController();

    // Setup ActionBar
    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
    // Setup NavigationView
    NavigationUI.setupWithNavController(navView, navController);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (navView == null) {
      getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return NavigationUI.onNavDestinationSelected(
            item, Navigation.findNavController(this, R.id.nav_host_fragment))
        || super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(
        Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
  }
}
