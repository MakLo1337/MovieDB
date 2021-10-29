package com.latihan.moviedb.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.Credits;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.model.UpComing;
import com.latihan.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {

    private static MovieRepo repository;

    private MovieRepo() {
    }

    public static MovieRepo getInstance() {
        if (repository == null) {
            repository = new MovieRepo();
        }
        return repository;
    }

    public MutableLiveData<Movies> getMovieData(String movieId) {
        final MutableLiveData<Movies> result = new MutableLiveData<>();
        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
        return result;
    }

    //get nowplaying section
    public MutableLiveData<NowPlaying> getNowPlayingData(int page) {
        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();
        ApiService.endPoint().getNowPlaying(Const.API_KEY, page).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });
        return result;
    }

    //get nowplaying section
    public MutableLiveData<UpComing> getUpComingData(int page){
        final MutableLiveData<UpComing> result = new MutableLiveData<>();
        ApiService.endPoint().getUpComing(Const.API_KEY, page).enqueue(new Callback<UpComing>() {
            @Override
            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UpComing> call, Throwable t) {

            }
        });
        return result;
    }

    //get credits data
    public MutableLiveData<Credits> getCreditsData(String movieId){
        final MutableLiveData<Credits> result = new MutableLiveData<>();
        ApiService.endPoint().getCredits(movieId, Const.API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
        return result;
    }

//    //get nowplaying section
//    public MutableLiveData<NowPlaying> getNowPlayingDataPage(String pagenum) {
//        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();
//        ApiService.endPoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
//            @Override
//            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
//                result.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<NowPlaying> call, Throwable t) {
//
//            }
//        });
//        return result;
//    }
}
