package com.example.newsapp.ui.selectedcategory;

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

public class SelectedCategoryRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<SelectedCategory> selectedCategoryArrayList;

    static class SelectedCategoryViewHolder1 extends RecyclerView.ViewHolder {
        TextView txtArticleTitle1;
        TextView txtArticleContent1;
        TextView txtArticleAuthor1;
        TextView txtArticleDate1;
        ImageView imgArticleImage1;
        ImageView imgArticleShare1;
        CardView cardView1;


        public SelectedCategoryViewHolder1(@NonNull View itemView) {
            super(itemView);
            txtArticleTitle1 = itemView.findViewById(R.id.txt_selected_article_title_1);
            txtArticleContent1 = itemView.findViewById(R.id.txt_selected_article_content_1);
            txtArticleAuthor1 = itemView.findViewById(R.id.txt_publisher_rv_for_you_logged_in);
            txtArticleDate1 = itemView.findViewById(R.id.txt_date_rv_for_you_logged_in);
            imgArticleImage1 = itemView.findViewById(R.id.img_selected_article_1);
            imgArticleShare1 = itemView.findViewById(R.id.toggleButton_share_rv_for_you_logged_in);
            cardView1 = itemView.findViewById(R.id.cardview_selected_article_1);
        }
    }

    static class SelectedCategoryViewHolder2 extends RecyclerView.ViewHolder {
        TextView txtArticleTitle2;
        TextView txtArticleContent2;
        TextView txtArticleDate2;
        ImageView imgArticleImage2;
        ImageView imgArticleShare2;
        CardView cardView2;

        public SelectedCategoryViewHolder2(@NonNull View itemView) {
            super(itemView);
            txtArticleTitle2 = itemView.findViewById(R.id.txt_selected_article_title_2);
            txtArticleContent2 = itemView.findViewById(R.id.txt_selected_article_content_2);
            txtArticleDate2 = itemView.findViewById(R.id.txt_selected_article_date_2);
            imgArticleImage2 = itemView.findViewById(R.id.img_selected_article_2);
            imgArticleShare2 = itemView.findViewById(R.id.img_selected_article_share_2);
            cardView2 = itemView.findViewById(R.id.cardview_selected_article_2);


        }
    }

    public SelectedCategoryRvAdapter(ArrayList<SelectedCategory> selectedCategoryArrayList) {
        this.selectedCategoryArrayList = selectedCategoryArrayList;
        //Constructor
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_selected_item_view_1, parent, false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_selected_item_view_2, parent, false);

        SelectedCategoryViewHolder1 selectedCategoryViewHolder1 = new SelectedCategoryViewHolder1(view1);
        SelectedCategoryViewHolder2 selectedCategoryViewHolder2 = new SelectedCategoryViewHolder2(view2);

        if (getItemViewType(viewType) == 0) {
            return selectedCategoryViewHolder1;

        } else return selectedCategoryViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SelectedCategory currentitem = selectedCategoryArrayList.get(position);
        if (holder.getItemViewType() == 0) {
            SelectedCategoryViewHolder1 viewHolder1 = (SelectedCategoryViewHolder1) holder;
            viewHolder1.txtArticleTitle1.setText(currentitem.getArticleTitle());
            viewHolder1.txtArticleContent1.setText(currentitem.getArticleContent());
            viewHolder1.txtArticleAuthor1.setText(currentitem.getArticleAuthor());
            viewHolder1.txtArticleDate1.setText(currentitem.getArticleDate());
            Picasso.get().load(currentitem.getArticleImageUrl()).into(viewHolder1.imgArticleImage1);
            viewHolder1.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentitem.getArticleURL()));
                    view.getContext().startActivity(browserIntent);
                }
            });


        } else {
            SelectedCategoryViewHolder2 viewHolder2 = (SelectedCategoryViewHolder2) holder;
            viewHolder2.txtArticleTitle2.setText(currentitem.getArticleTitle());
            viewHolder2.txtArticleContent2.setText(currentitem.getArticleContent());
            viewHolder2.txtArticleDate2.setText(currentitem.getArticleDate());
            if (currentitem.getArticleImageUrl().equals("https://www.nytimes.com/")) {
                viewHolder2.imgArticleImage2.setVisibility(View.GONE);
            }
            Picasso.get().load(currentitem.getArticleImageUrl()).into(viewHolder2.imgArticleImage2);
            viewHolder2.cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentitem.getArticleURL()));
                    view.getContext().startActivity(browserIntent);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return selectedCategoryArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    
}
