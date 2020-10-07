package com.example.newsapp.ui.foryou;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ForYouLoggedInRecyclerViewAdapter extends RecyclerView.Adapter<ForYouLoggedInRecyclerViewAdapter.ForYouLoggedInRecyclerViewViewHolder> {
    public ArrayList<ForYouLoggedInItems> forYouLoggedInArrayList;

    static class ForYouLoggedInRecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView txtSectionName;
        TextView txtArticleTitle;
        TextView txtArticleContent;
        TextView txtArticleAuthor;
        TextView txtArticleDate;
        ImageView imgArticleImage;
        ImageView imgArticleShare;
        ToggleButton toggleBookmark;
        CardView cardView;


        public ForYouLoggedInRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSectionName = itemView.findViewById(R.id.txt_section_rv_for_you_logged_in);
            txtArticleTitle = itemView.findViewById(R.id.txt_article_title_rv_for_you_logged_in);
            txtArticleContent = itemView.findViewById(R.id.txt_article_content_rv_for_you_logged_in);
            txtArticleAuthor = itemView.findViewById(R.id.txt_publisher_rv_for_you_logged_in);
            txtArticleDate = itemView.findViewById(R.id.txt_date_rv_for_you_logged_in);
            imgArticleImage = itemView.findViewById(R.id.img_article_rv_for_you_logged_in);
            imgArticleShare = itemView.findViewById(R.id.toggleButton_share_rv_for_you_logged_in);
            cardView = itemView.findViewById(R.id.cardview_rv_for_you_logged_in);
            toggleBookmark = itemView.findViewById(R.id.toggleButton_bookmark_rv_for_you_logged_in);
        }
    }

    public ForYouLoggedInRecyclerViewAdapter(ArrayList<ForYouLoggedInItems> forYouLoggedInArrayList) {
        this.forYouLoggedInArrayList = forYouLoggedInArrayList;
        //Constructor
    }

    @NonNull
    @Override
    public ForYouLoggedInRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_you_logged_in, parent, false);
        return new ForYouLoggedInRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForYouLoggedInRecyclerViewViewHolder holder, int position) {
        final ForYouLoggedInItems currentitem = forYouLoggedInArrayList.get(position);
        holder.txtSectionName.setText(currentitem.getSectionName());
        holder.txtArticleTitle.setText(currentitem.getArticleTitle());
        holder.txtArticleContent.setText(currentitem.getArticleContent());
        holder.txtArticleAuthor.setText(currentitem.getArticleAuthor());
        holder.txtArticleDate.setText(currentitem.getArticleDate());
        // holder.toggleBookmark
        //holder.imgArticleShare
        if (currentitem.getArticleImageUrl().equals("https://www.nytimes.com/")) {
            Picasso.get().load("https://cdn.shopify.com/s/assets/no-image-2048-5e88c1b20e087fb7bbe9a3771824e743c244f437e4f8ba93bbf7b11b53f7824c.gif").into(holder.imgArticleImage);
        } else {
            Picasso.get().load(currentitem.getArticleImageUrl()).into(holder.imgArticleImage);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentitem.getArticleURL()));
                view.getContext().startActivity(browserIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return forYouLoggedInArrayList.size();
    }
}
