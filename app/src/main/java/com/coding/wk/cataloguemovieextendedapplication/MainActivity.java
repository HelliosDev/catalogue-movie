package com.coding.wk.cataloguemovieextendedapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.coding.wk.cataloguemovieextendedapplication.fragments.FavoriteFragment;
import com.coding.wk.cataloguemovieextendedapplication.fragments.HomeFragment;
import com.coding.wk.cataloguemovieextendedapplication.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    private Fragment fragment = null;
    private final String EXTRA_FRAGMENT = "ExtraFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        setNavigation();

        if (savedInstanceState != null){
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, EXTRA_FRAGMENT);
            replaceFragment(fragment);
        }
        else{
            fragment = new HomeFragment();
            replaceFragment(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title = "";
        switch (id){
            case R.id.nav_home:
                title = getResources().getString(R.string.app_name);
                fragment = new HomeFragment();
                break;
            case R.id.nav_search:
                title = getResources().getString(R.string.title_search);
                fragment = new SearchFragment();
                break;
            case R.id.nav_favorite:
                title = getResources().getString(R.string.title_favorite);
                fragment = new FavoriteFragment();
                break;
            default:
        }
        if(fragment != null){
            replaceFragment(fragment);
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigation(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, EXTRA_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }
}
