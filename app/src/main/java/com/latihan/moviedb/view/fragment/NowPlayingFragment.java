package com.latihan.moviedb.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.latihan.moviedb.R;
import com.latihan.moviedb.adapter.NowPlayingAdapter;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.viewModel.MovieVM;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        loadingAnimation = ProgressDialog.show(getActivity(),"","",true);
    }

    private RecyclerView rv_now_playing;
    private ProgressBar progressBar;
    private MovieVM movieVM;
    List<NowPlaying.Results> dataset = new ArrayList<>();
    private int defaultPage = 1;
    private int totalPage = 1;
    NowPlayingAdapter adapter;
    private boolean isLoading;
    AlphaInAnimationAdapter animationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        rv_now_playing = view.findViewById(R.id.rv_now_playing);
        progressBar = view.findViewById(R.id.progressBar2);
        initView();
        return view;
    }

    private void initView() {
        dataset.clear();
        defaultPage = 1;
        movieVM = new ViewModelProvider(getActivity()).get(MovieVM.class);
        adapter = new NowPlayingAdapter(getActivity());
        adapter.setListNowPlaying(dataset);
        isLoading = false;

        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        rv_now_playing.setLayoutManager(rvLayoutManager);
        animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setFirstOnly(false);
        rv_now_playing.setAdapter(animationAdapter);
        getNowPlaying();
        rv_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager rvLayout = (LinearLayoutManager) rv_now_playing.getLayoutManager();
                if (!isLoading) {
                    if (rvLayout != null && rvLayout.findLastCompletelyVisibleItemPosition() == dataset.size() - 1) {
                        if (defaultPage <= totalPage) {
                            isLoading = true;
                            defaultPage += 1;
                            dataset.add(null);
                            animationAdapter.notifyDataSetChanged();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dataset.remove(null);
                                    getNowPlaying();
                                    isLoading = false;
                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
    }

    private void getNowPlaying() {
        movieVM.getNowPlaying(defaultPage);
        movieVM.getResultGetNowPlaying().observe(getActivity(), nowPlayingResponse -> {
            if (nowPlayingResponse == null) {
                if(defaultPage == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    rv_now_playing.setVisibility(View.GONE);
                }
            } else if (nowPlayingResponse != null) {
                if(defaultPage == 1) {
                    progressBar.setVisibility(View.GONE);
                    rv_now_playing.setVisibility(View.VISIBLE);
                    totalPage = nowPlayingResponse.getTotal_pages();
                    if (nowPlayingResponse.getResults() != null) {
                        dataset.addAll(nowPlayingResponse.getResults());
                        animationAdapter.notifyDataSetChanged();
                    }
                } else {
                    totalPage = nowPlayingResponse.getTotal_pages();
                    if (nowPlayingResponse.getResults() != null) {
                        dataset.addAll(nowPlayingResponse.getResults());
                        animationAdapter.notifyDataSetChanged();
                    }
                }
            }

        });

    }
}