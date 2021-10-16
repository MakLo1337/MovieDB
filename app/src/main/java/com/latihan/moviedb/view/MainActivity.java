package com.latihan.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.latihan.moviedb.R;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.viewModel.MovieVM;

public class MainActivity extends AppCompatActivity {

    private MovieVM viewModel;
    private Button main_button;
    private TextView txt_show;
    private TextInputLayout til_main;
    private ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieVM.class);

        til_main = findViewById(R.id.til_main);
        txt_show = findViewById(R.id.txt_show_main);
        main_button = findViewById(R.id.main_Button);
        img_view = findViewById(R.id.mainPoster_img);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = til_main.getEditText().getText().toString().trim();
                if (movieId.isEmpty()) {
                    til_main.setError("Please fill the movie id!");
                } else {
                    til_main.setError(null);
                    viewModel.getMovieById(movieId);
                    viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
                }
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if (movies == null) {
                txt_show.setText("Movie id is not found!");
            } else {
                String title = movies.getTitle();
                txt_show.setText(title);
                Glide.with(getBaseContext())
                        .load(Const.IMG_URL + movies.getPoster_path())
                        .into(img_view);
            }
        }
    };
}