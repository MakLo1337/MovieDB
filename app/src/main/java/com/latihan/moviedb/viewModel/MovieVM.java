package com.latihan.moviedb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.latihan.moviedb.model.Credits;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.model.UpComing;
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
    public void getNowPlaying(int page){
        resultGetNowPlaying = repo.getNowPlayingData(page);
    }
    public LiveData<NowPlaying> getResultGetNowPlaying(){
        return resultGetNowPlaying;
    }

    //VM get Nowplaying
    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
    public void getUpComing(int page){
        resultGetUpComing = repo.getUpComingData(page);
    }
    public LiveData<UpComing> getResultUpComing(){
        return resultGetUpComing;
    }

    //VM get Credits
    private MutableLiveData<Credits> resultGetCredits = new MutableLiveData<>();
    public void getCredits(String movieId) {
        resultGetCredits = repo.getCreditsData(movieId);
    }
    public LiveData<Credits> getResutltCredits(){
        return resultGetCredits;
    }

}
