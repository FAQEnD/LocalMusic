package ua.com.free.localmusic.localmusic.ui.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ua.com.free.localmusic.R;
import ua.com.free.localmusic.di.AppComponent;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.localmusic.ui.adapter.SongAdapter;
import ua.com.free.localmusic.localmusic.ui.screen.base.BaseScreen;
import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;
import ua.com.free.localmusic.localmusic.ui.vh.SongViewHolder;
import ua.com.free.localmusic.models.Song;

public class MainScreen extends BaseScreen implements NavigationView.OnNavigationItemSelectedListener, IMainScreen {

    private static final String TAG = "MainScreen";

    @Inject
    IMainScreenController controller;

    private SongAdapter mSongAdapter;
    private MaterialSearchView mSearchView;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_gallery:

                break;
            case R.id.next:
                controller.askToPlayNextSong();
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void updateData(List<Song> songs) {
        mSongAdapter.setData(songs);
    }

    @Override
    public void notifyUser(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected void setScreen() {
        controller.setScreen(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDrawer(toolbar);
        setRecyclerView();
        setupSearchView();
        controller.onCreate(this);
    }

    private void setupSearchView() {
        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, query);
                controller.askToSearchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSongAdapter = new SongAdapter(new ArrayList<>(), new SongViewHolder.IViewHolderClickListener() {

            @Override
            public void onItemClick(int pos, View v) {
                Log.d(TAG, "item with pos: " + pos + " was clicked");
                controller.askToUpdatePlaylist(mSongAdapter.getData());
                controller.askToPlaySong(pos);
            }

            @Override
            public void onItemLongClick(int pos, View v) {
                Log.d(TAG, "item with pos: " + pos + " was long clicked");
            }
        });
        recyclerView.setAdapter(mSongAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
