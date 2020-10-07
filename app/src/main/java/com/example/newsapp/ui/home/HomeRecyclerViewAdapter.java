package com.example.newsapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    private ArrayList<HomeArticles> homeArticles;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView contentTextView;
        TextView dateTextView;
        TextView publisherTextView;
        ImageView articleImageView;
        CardView articleCardView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txt_selected_article_title_2);
            contentTextView = itemView.findViewById(R.id.txt_article_content_home);
            dateTextView = itemView.findViewById(R.id.txt_article_date_home);
            publisherTextView = itemView.findViewById(R.id.txt_article_author_home);
            articleImageView = itemView.findViewById(R.id.img_article_home);
            articleCardView=itemView.findViewById(R.id.cardview_rv_home);
        }
    }

    public HomeRecyclerViewAdapter(ArrayList<HomeArticles> homeArticles) {
        this.homeArticles = homeArticles;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final HomeArticles currentHomeArticle = homeArticles.get(position);

        holder.titleTextView.setText(currentHomeArticle.getArticleTitle());
        holder.contentTextView.setText(currentHomeArticle.getArticleContent());
        Picasso.get().load(currentHomeArticle.getArticleImageURL()).into(holder.articleImageView);
        holder.dateTextView.setText(currentHomeArticle.getArticleDate());
        holder.publisherTextView.setText(currentHomeArticle.getArticleAuthor());
        holder.articleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentHomeArticle.getArticleSiteUrl()));
                view.getContext().startActivity(browserIntent);

                }
        });
    }

    @Override
    public int getItemCount() {
        return homeArticles.size();
    }

}
