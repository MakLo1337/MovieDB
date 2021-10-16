package com.latihan.moviedb.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.latihan.moviedb.R;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.viewModel.MovieVM;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieVM viewModel;
    private TextView lbl_mvd_title, lbl_mvd_subtitle, lbl_mvd_genre, lbl_mvd_rate;
    private ImageView img_view_mvd;
    private String movieId = "";
    private Toolbar toolbar2;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("movie_id");

        lbl_mvd_title = findViewById(R.id.lbl_mvd_title);
        lbl_mvd_subtitle = findViewById(R.id.lbl_mvd_subtitle);
        lbl_mvd_genre = findViewById(R.id.lbl_mvd_genre);
        lbl_mvd_rate = findViewById(R.id.lbl_mvd_rate);
        img_view_mvd = findViewById(R.id.img_view_mvd);

        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieVM.class);
        viewModel.getMovieById(movieId);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);

    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String genres = "";
            String rate = String.valueOf(movies.getVote_average());
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if((i+1) != movies.getGenres().size()){
                    genres += movies.getGenres().get(i).getName() + ", ";
                } else {
                    genres += movies.getGenres().get(i).getName();
                }
            }
            lbl_mvd_title.setText(title);
            lbl_mvd_genre.setText(genres);
            lbl_mvd_rate.setText(rate);
            lbl_mvd_subtitle.setText(movies.getOverview());
            Glide.with(getBaseContext())
                    .load(Const.IMG_URL + movies.getPoster_path())
                    .into(img_view_mvd);

        }
    };

    @Override
    public void onBackPressed(){
        finish();
    }
}