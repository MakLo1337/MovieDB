package com.latihan.moviedb.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.latihan.moviedb.R;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.viewModel.MovieVM;

public class NowPlayingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieVM view_model;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.rv_nowplaying);
        view_model = new ViewModelProvider(NowPlayingActivity.this).get(MovieVM.class);
        view_model.getNowPlaying(2);
//        view_model.getResultGetNowPlaying().observe(NowPlayingActivity.this, showNowPlaying);
    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            recyclerView.setLayoutManager(new LinearLayoutManager(NowPlayingActivity.this));
//            NowPlayingAdapter adapter = new NowPlayingAdapter(NowPlayingActivity.this);
//            adapter.setListNowPlaying(nowPlaying.getResults());
//            recyclerView.setAdapter(adapter);
        }
    };
}