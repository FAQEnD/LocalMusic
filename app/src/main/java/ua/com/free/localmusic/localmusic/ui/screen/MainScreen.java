package ua.com.free.localmusic.localmusic.ui.screen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.free.localmusic.R;
import ua.com.free.localmusic.api.youtube.YoutubeAPI;
import ua.com.free.localmusic.di.AppComponent;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.localmusic.ui.adapter.SongAdapter;
import ua.com.free.localmusic.localmusic.ui.screen.base.BaseScreen;
import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;
import ua.com.free.localmusic.localmusic.ui.vh.SongViewHolder;

public class MainScreen extends BaseScreen
        implements NavigationView.OnNavigationItemSelectedListener, IMainScreen {

    private static final String TAG = "MainScreen";
    @Inject
    IMainScreenController controller;
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

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
            case R.id.nav_slideshow:

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
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected void setScreen() {
        controller.setScreen(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFab();
        setDrawer(toolbar);
        setRecyclerView();

        YoutubeAPI youtubeAPI = new YoutubeAPI();
        youtubeAPI.search(this, "Hurts")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(searchResults -> {
                    Log.d(TAG, searchResults.toString());
                    mSongAdapter.setData(searchResults);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, throwable.getMessage());
                    Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribe();
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSongAdapter = new SongAdapter(new ArrayList<>(), new SongViewHolder.IViewHolderClickListener() {

            @Override
            public void onItemClick(int pos, View v) {
                Log.d(TAG, "item with pos: " + pos + " was clicked");
            }

            @Override
            public void onItemLongClick(int pos, View v) {
                Log.d(TAG, "item with pos: " + pos + " was long clicked");
            }
        });
        mRecyclerView.setAdapter(mSongAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void setFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

        });
    }

    private void setDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
