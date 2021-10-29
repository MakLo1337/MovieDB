package com.latihan.moviedb.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.latihan.moviedb.R;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.model.UpComing;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UpComingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private Context context;
    private List<UpComing.Results> listUpComing;

    public UpComingAdapter(Context context){
        this.context = context;
    }
    private List<UpComing.Results> getListUpComing() {
        return listUpComing;
    }
    public void setListUpComing(List<UpComing.Results> listUpComing) {
        this.listUpComing = listUpComing;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_upcoming, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_endless, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listUpComing.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listUpComing == null ? 0 : listUpComing.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView img_view;
        TextView lbl_title, lbl_subtitle, lbl_sub2;
        ProgressBar progressBar;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_card_upcoming);
            img_view = itemView.findViewById(R.id.img_view_crd_upcoming);
            lbl_title = itemView.findViewById(R.id.lbl_title_crd_upcoming);
            lbl_subtitle = itemView.findViewById(R.id.lbl_subtitle_crd_upcoming);
            lbl_sub2 = itemView.findViewById(R.id.lbl_subtitle2_crd_upcoming);
            progressBar = itemView.findViewById(R.id.crd_img_progress_upcoming);
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position){
        final UpComing.Results results = getListUpComing().get(position);
        viewHolder.lbl_title.setText(results.getTitle());
        viewHolder.lbl_subtitle.setText(results.getOverview());
        viewHolder.lbl_sub2.setText(String.valueOf(results.getRelease_date()));
        Glide.with(context)
                .load(Const.IMG_URL + results.getPoster_path())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0, RoundedCornersTransformation.CornerType.ALL)))
                .into(viewHolder.img_view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, MovieDetailsActivity.class);
//                intent.putExtra("movie_id", ""+results.getId());
//                context.startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + results.getId());
                Navigation.findNavController(view)
                        .navigate(R.id.action_upcomingFragment_to_movieDetailsFragment, bundle);
            }
        });
    }
}
