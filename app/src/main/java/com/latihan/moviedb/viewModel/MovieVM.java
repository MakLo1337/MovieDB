package com.latihan.moviedb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.repo.MovieRepo;

public class MovieVM extends AndroidViewModel {

    private MovieRepo repo;

    public MovieVM(@NonNull Application application){
        super(application);
        repo = MovieRepo.getInstance();
    }

    //VM get movie by id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId){
        resultGetMovieById = repo.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }

    //VM get Nowplaying
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(){
        resultGetNowPlaying = repo.getNowPlayingData();
    }
    public LiveData<NowPlaying> getResultGetNowPlaying(){
        return resultGetNowPlaying;
    }

}
