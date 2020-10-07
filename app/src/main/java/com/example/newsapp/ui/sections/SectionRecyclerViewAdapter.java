package com.example.newsapp.ui.sections;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.ui.selectedcategory.SelectedCategoryActivity;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {
    public ArrayList<Sections> arrayList;
    Context context;

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ConstraintLayout constraintLayout;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_section);
            textView = itemView.findViewById(R.id.text_section);
            constraintLayout=itemView.findViewById(R.id.constraint_section);

        }
    }

    public SectionRecyclerViewAdapter(ArrayList<Sections> sectionsArrayList) {
        this.arrayList=sectionsArrayList;

    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_sections, parent, false);
        SectionViewHolder sectionViewHolder = new SectionViewHolder(view);
        return sectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        final Sections currentItem = arrayList.get(position);
        holder.textView.setText(currentItem.getSectionName());
        holder.imageView.setImageResource(currentItem.getSectionImage());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  AppCompatActivity activity= (AppCompatActivity) view.getContext();
                SelectedCategoryFragment fragment=new SelectedCategoryFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment_sections,fragment).addToBackStack(null).commit();
               Bundle bundle=new Bundle();
                bundle.putString("searchedCategory",currentItem.getSectionName());
                fragment.setArguments(bundle);*/ //How to start fragment from Adapter

                Intent intent = new Intent(view.getContext(), SelectedCategoryActivity.class);
                intent.putExtra("searchedCategory",currentItem.getSectionName());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
