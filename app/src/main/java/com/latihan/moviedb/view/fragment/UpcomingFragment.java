package com.latihan.moviedb.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.latihan.moviedb.R;
import com.latihan.moviedb.adapter.NowPlayingAdapter;
import com.latihan.moviedb.adapter.UpComingAdapter;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.model.UpComing;
import com.latihan.moviedb.viewModel.MovieVM;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
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
    }

    private RecyclerView rv_upcoming;
    private ProgressBar progressBar;
    private MovieVM movieVM;
    List<UpComing.Results> dataset = new ArrayList<>();
    private int defaultPage = 1;
    private int totalPage = 1;
    UpComingAdapter adapter;
    private boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        rv_upcoming = view.findViewById(R.id.rv_upcoming);
        progressBar = view.findViewById(R.id.progressBarUp);
        initView();
        return view;
    }

    private void initView() {
        dataset.clear();
        defaultPage = 1;
        movieVM = new ViewModelProvider(getActivity()).get(MovieVM.class);
        adapter = new UpComingAdapter(getActivity());
        adapter.setListUpComing(dataset);

        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        rv_upcoming.setLayoutManager(rvLayoutManager);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setFirstOnly(false);
        rv_upcoming.setAdapter(animationAdapter);
        rv_upcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager rvLayout = (LinearLayoutManager) rv_upcoming.getLayoutManager();
                if (!isLoading) {
                    if (rvLayout != null && rvLayout.findLastCompletelyVisibleItemPosition() == dataset.size() - 1) {
                        if (defaultPage <= totalPage) {
                            if (defaultPage <= totalPage) {
                                isLoading = true;
                                defaultPage += 1;
                                dataset.add(null);
                                adapter.notifyDataSetChanged();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dataset.remove(null);
                                        getUpcoming();
                                        isLoading = false;
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            }
        });
        getUpcoming();
    }

    private void getUpcoming() {
        movieVM.getUpComing(defaultPage);
        movieVM.getResultUpComing().observe(getActivity(), upComingResponse -> {
            if (upComingResponse == null) {
                if(defaultPage == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    rv_upcoming.setVisibility(View.GONE);
                }
            } else if (upComingResponse != null) {
                if(defaultPage == 1) {
                    progressBar.setVisibility(View.GONE);
                    rv_upcoming.setVisibility(View.VISIBLE);
                    totalPage = upComingResponse.getTotal_pages();
                    if (upComingResponse.getResults() != null) {
                        dataset.addAll(upComingResponse.getResults());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    totalPage = upComingResponse.getTotal_pages();
                    if (upComingResponse.getResults() != null) {
                        dataset.addAll(upComingResponse.getResults());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}