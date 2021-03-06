package com.github.hyota.asciiartboardreader.presentation.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.hyota.asciiartboardreader.R;
import com.github.hyota.asciiartboardreader.domain.entity.Board;
import com.github.hyota.asciiartboardreader.presentation.boardlist.BoardListFragment;
import com.github.hyota.asciiartboardreader.presentation.common.BaseActivity;
import com.github.hyota.asciiartboardreader.presentation.common.FloatingActionButtonCallback;
import com.github.hyota.asciiartboardreader.presentation.common.ActionbarCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View,
        HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener,
        ActionbarCallback, FloatingActionButtonCallback,
        BoardListFragment.OnBoardListFragmentInteractionListener {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    MainContract.Presenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag("main") == null) {
            // 初回時には取得できないため初期化する
            manager.beginTransaction()
                    .add(R.id.container, BoardListFragment.newInstance(), "main")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void setTitle(@NonNull String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener listener) {
        fab.setOnClickListener(listener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        fab.setEnabled(enabled);
    }

    @Override
    public void setImage(int resId) {
        Drawable drawable = Objects.requireNonNull(getDrawable(android.R.drawable.ic_input_add));
        drawable.setTint(ContextCompat.getColor(this, android.R.color.white));
        fab.setImageDrawable(drawable);
    }

    @Override
    public void onSelectBoard(@NonNull Board item) {
        Timber.d("select item title = %s, url = %s", item.getTitle(), item.getUrl());
    }
}
