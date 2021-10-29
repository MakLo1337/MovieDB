package com.latihan.moviedb.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.latihan.moviedb.R;
import com.latihan.moviedb.helper.Const;
import com.latihan.moviedb.model.Credits;
import com.latihan.moviedb.model.NowPlaying;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class CreditsCastAdapter extends RecyclerView.Adapter<CreditsCastAdapter.CardViewViewHolder>
{
    private Context context;
    private List<Credits.Cast> CreditsList;
    private List<Credits.Cast> getCreditsList()
    {
        return CreditsList;
    }
    public void setCreditsList(List<Credits.Cast> CreditsList) {
        this.CreditsList = CreditsList;
    }
    public CreditsCastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crd_cast, parent, false);
        return new CreditsCastAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final Credits.Cast results = getCreditsList().get(position);
        holder.crd_cast_name.setText(results.getOriginal_name());
        holder.crd_cast_oriname.setText(results.getCharacter());
        Glide.with(context)
                .load(Const.IMG_URL + results.getProfile_path())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.crd_cast_img);
    }

    @Override
    public int getItemCount() {
        return getCreditsList().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView crd_cast_img;
        TextView crd_cast_name, crd_cast_oriname;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.crd_cast);
            crd_cast_img = itemView.findViewById(R.id.crd_cast_img);
            crd_cast_name = itemView.findViewById(R.id.crd_cast_name);
            crd_cast_oriname = itemView.findViewById(R.id.crd_cast_oriname);
        }
    }
}
