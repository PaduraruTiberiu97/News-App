package com.example.newsapp.ui.foryou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.database.FavoriteForYou;

import java.util.ArrayList;

import static com.example.newsapp.ui.foryou.ForYouFragment.database;

public class ForYouRecyclerViewAdapter extends RecyclerView.Adapter<ForYouRecyclerViewAdapter.ForYouViewHolder> {
    private ArrayList<ForYouItems> forYouItems;
    private OnFollowBtnClick onFollowBtnClickListener;



    public static class ForYouViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubSection;
        ImageView imgSubSection;
        ToggleButton btnFollow;
        CardView cardView;

        public ForYouViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubSection = itemView.findViewById(R.id.txt_rv_foryou_subsection);
            imgSubSection = itemView.findViewById(R.id.img_rv_foryou);
            btnFollow = itemView.findViewById(R.id.btn_rv_foryou_follow);
            cardView = itemView.findViewById(R.id.cardview_rv_foryou);

        }
    }

    public ForYouRecyclerViewAdapter(ArrayList<ForYouItems> forYouItems, OnFollowBtnClick onFollowBtnClickListener) {
        this.onFollowBtnClickListener = onFollowBtnClickListener;
        this.forYouItems = forYouItems;
    }

    @NonNull
    @Override
    public ForYouViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_foryou, parent, false);
        return new ForYouViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ForYouViewHolder holder, int position) {

        final ForYouItems currentForYouItem = forYouItems.get(position);
        final FavoriteForYou favoriteForYou = new FavoriteForYou();
        favoriteForYou.setId(position);

        holder.txtSubSection.setText(currentForYouItem.getSubSectionName());
        holder.imgSubSection.setImageResource(currentForYouItem.getImage());

        if (database.forYouDao().isFavorite(currentForYouItem.getId()) == 1) {
            holder.btnFollow.setChecked(true);
            holder.btnFollow.setTextColor(Color.parseColor("#578DB8"));
            holder.btnFollow.setBackground(ContextCompat.getDrawable(holder.btnFollow.getContext(), R.drawable.toggle_border));

        } else {
            holder.btnFollow.setChecked(false);
            holder.btnFollow.setTextColor(Color.parseColor("#ffffff"));
            holder.btnFollow.setBackgroundDrawable(ContextCompat.getDrawable(holder.btnFollow.getContext(), R.drawable.toggle_fill));
           // holder.btnFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
        }

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String favorite = currentForYouItem.getSubSectionName();
                int id = currentForYouItem.getId();
                favoriteForYou.setId(id);
                favoriteForYou.setFavoriteSectionName(favorite);


                if (database.forYouDao().isFavorite(id) != 1) {
                    holder.btnFollow.setChecked(true);
                    holder.btnFollow.setTextColor(Color.parseColor("#578DB8"));
                    holder.btnFollow.setBackground(ContextCompat.getDrawable(holder.btnFollow.getContext(), R.drawable.toggle_border));
                    holder.btnFollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    database.forYouDao().insert(favoriteForYou);
                    onFollowBtnClickListener.onFlwClick();
                } else {
                    holder.btnFollow.setChecked(false);
                    holder.btnFollow.setTextColor(Color.parseColor("#ffffff"));
                    holder.btnFollow.setBackgroundDrawable(ContextCompat.getDrawable(holder.btnFollow.getContext(), R.drawable.toggle_fill));
                   // holder.btnFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);

                    database.forYouDao().delete(favoriteForYou);
                    onFollowBtnClickListener.onFlwClick();
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ForYouSelectedActivity.class);
                intent.putExtra("forYouSelectedCategory", currentForYouItem.getSubSectionName());
                intent.putExtra("forYouSelectedCategoryId", currentForYouItem.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forYouItems.size();
    }


}