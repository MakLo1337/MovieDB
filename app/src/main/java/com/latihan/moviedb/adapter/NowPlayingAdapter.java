package com.latihan.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.latihan.moviedb.R;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.view.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingHolder> {

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;

    public NowPlayingAdapter(Context context) {
        this.context = context;
    }

    private List<NowPlaying.Results> getListNowPlaying(){return listNowPlaying;}

    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying) {
        this.listNowPlaying = listNowPlaying;
    }



    @NonNull
    @Override
    public NowPlayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent,
                false);
        return new NowPlayingAdapter.NowPlayingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingHolder holder, int position) {
        final NowPlaying.Results results = getListNowPlaying().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_subtitle.setText(results.getOverview());
        holder.lbl_sub2.setText(results.getRelease_date());
        Glide.with(context)
                .load(Const.IMG_URL + results.getPoster_path())
                .into(holder.img_view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_id", ""+results.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNowPlaying.size();
    }

    public class NowPlayingHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img_view;
        TextView lbl_title, lbl_subtitle, lbl_sub2;
        public NowPlayingHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_card_nowplaying);
            img_view = itemView.findViewById(R.id.img_view_crd_nowplaying);
            lbl_title = itemView.findViewById(R.id.lbl_title_crd_nowplaying);
            lbl_subtitle = itemView.findViewById(R.id.lbl_subtitle_crd_nowplaying);
            lbl_sub2 = itemView.findViewById(R.id.lbl_subtitle2_crd_nowplaying);
        }
    }
}
