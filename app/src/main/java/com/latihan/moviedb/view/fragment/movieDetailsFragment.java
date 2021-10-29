package com.latihan.moviedb.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.latihan.moviedb.R;
import com.latihan.moviedb.adapter.CreditsCastAdapter;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.Credits;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.viewModel.MovieVM;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link movieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class movieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public movieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment movieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static movieDetailsFragment newInstance(String param1, String param2) {
        movieDetailsFragment fragment = new movieDetailsFragment();
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

    private MovieVM viewModel;
    private LinearLayout ly_scv_prc;
    private ScrollView scrollView;
    private TextView lbl_mvdf_title, lbl_mvdf_subtitle, lbl_mvdf_genre, lbl_mvdf_rate,
            lbl_mvdf_release, lbl_mvdf_popularity;
    private ImageView img_view_mvdf, backdrop_mvdf_img;
    private RecyclerView rv_cast;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        backdrop_mvdf_img = view.findViewById(R.id.backdrop_mvdf_img);
        lbl_mvdf_title = view.findViewById(R.id.lbl_mvdf_title);
        lbl_mvdf_subtitle = view.findViewById(R.id.lbl_mvdf_subtitle);
        lbl_mvdf_genre = view.findViewById(R.id.lbl_mvdf_genre);
        lbl_mvdf_rate = view.findViewById(R.id.lbl_mvdf_rate);
        img_view_mvdf = view.findViewById(R.id.img_view_mvdf);
        lbl_mvdf_release = view.findViewById(R.id.lbl_mvdf_release);
        lbl_mvdf_popularity = view.findViewById(R.id.lbl_mvdf_popularity);
        rv_cast = view.findViewById(R.id.rv_cast);
        ly_scv_prc = view.findViewById(R.id.ly_scv_prc);
        progressBar = view.findViewById(R.id.progressBar3);
        scrollView = view.findViewById(R.id.mvdf_scroll);


        String movieID = getArguments().getString("movieId");
        viewModel = new ViewModelProvider(this).get(MovieVM.class);
        viewModel.getMovieById(movieID);
        viewModel.getResultGetMovieById().observe(getViewLifecycleOwner(), showResultMovie);
        viewModel.getCredits(movieID);
        viewModel.getResutltCredits().observe(getViewLifecycleOwner(), showCreditsCast);
        return view;


    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if (movies == null) {
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            } else if (movies != null) {
                progressBar.setVisibility(View.GONE);
                String title = movies.getTitle();
                String genres = "";
                String rate = String.valueOf(movies.getVote_average());
                String release = movies.getRelease_date();
                String popularity = String.valueOf(movies.getPopularity());
                for (int i = 0; i < movies.getGenres().size(); i++) {
                    if ((i + 1) != movies.getGenres().size()) {
                        genres += movies.getGenres().get(i).getName() + ", ";
                    } else {
                        genres += movies.getGenres().get(i).getName();
                    }
                }

                for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                    ImageView image = new ImageView(ly_scv_prc.getContext());
                    image.setBackground(getResources().getDrawable(R.drawable.imageback_shape));
                    image.setPadding(30, 10, 30, 10);
                    String logo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                    String name = movies.getProduction_companies().get(i).getName();
                    if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_not_supported_24, getActivity().getTheme()));
                    } else if (logo != "https://image.tmdb.org/3/t/p/w500/null") {
                        Glide.with(getActivity())
                                .load(logo)
//                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                                .into(image);
                    }
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                    lp.setMargins(20, 0, 20, 0);
                    image.setLayoutParams(lp);
                    ly_scv_prc.addView(image);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar snackbar = Snackbar.make(view, name, Snackbar.LENGTH_SHORT);
                            snackbar.setAnchorView(R.id.bottomNavigationView);
                            snackbar.show();
                        }
                    });
                }

                lbl_mvdf_title.setText(title);
                lbl_mvdf_genre.setText(genres);
                lbl_mvdf_rate.setText(rate);
                lbl_mvdf_subtitle.setText(movies.getOverview());
                lbl_mvdf_release.setText(release);
                lbl_mvdf_popularity.setText(popularity);
                Glide.with(getActivity())
                        .load(Const.IMG_URL + movies.getBackdrop_path())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 1)))
                        .into(backdrop_mvdf_img);
                Glide.with(getActivity())
                        .load(Const.IMG_URL + movies.getPoster_path())
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(img_view_mvdf);
                scrollView.setVisibility(View.VISIBLE);
            }
        }
    };

    private Observer<Credits> showCreditsCast = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            CreditsCastAdapter adapter = new CreditsCastAdapter(getActivity());
            adapter.setCreditsList(credits.getCast());
            AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
            animationAdapter.setFirstOnly(false);
//            rv_cast.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv_cast.setAdapter(animationAdapter);
        }
    };
}