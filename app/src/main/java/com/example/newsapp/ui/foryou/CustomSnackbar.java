package com.example.newsapp.ui.foryou;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.example.newsapp.R;
import com.example.newsapp.database.FavoriteForYouDatabase;
import com.google.android.material.snackbar.BaseTransientBottomBar;

public class CustomSnackbar extends BaseTransientBottomBar<CustomSnackbar> {
    public static FavoriteForYouDatabase database;

    protected CustomSnackbar(@NonNull ViewGroup parent, @NonNull View content, @NonNull com.google.android.material.snackbar.ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }


    private static class ContentViewCallback implements
            BaseTransientBottomBar.ContentViewCallback {

        // view inflated from custom layout
        private View content;

        public ContentViewCallback(View content) {
            this.content = content;
        }

        @Override
        public void animateContentIn(int delay, int duration) {
            // add custom *in animations for your views
            // e.g. original snackbar uses alpha animation, from 0 to 1
            ViewCompat.setScaleY(content, 0f);
            ViewCompat.animate(content)
                    .scaleY(1f).setDuration(duration)
                    .setStartDelay(delay);
        }

        @Override
        public void animateContentOut(int delay, int duration) {
            // add custom *out animations for your views
            // e.g. original snackbar uses alpha animation, from 1 to 0
            ViewCompat.setScaleY(content, 1f);
            ViewCompat.animate(content)
                    .scaleY(0f)
                    .setDuration(duration)
                    .setStartDelay(delay);
        }
    }

    public static CustomSnackbar make(ViewGroup parent, @Duration int duration) {
        // inflate custom layout
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View content = layoutInflater.inflate(R.layout.layout_snackbar, parent, false);

        // create snackbar with custom view
        ContentViewCallback callback = new ContentViewCallback(content);
        CustomSnackbar customSnackbar = new CustomSnackbar(parent, content, callback);

        // Remove black background padding on left and right
        customSnackbar.getView().setPadding(0, 0, 0, 0);

        // set snackbar duration
        customSnackbar.setDuration(duration);
        return customSnackbar;
    }

    public CustomSnackbar setText(CharSequence text) {
        TextView textView = getView().findViewById(R.id.textView_snackbar);
        textView.setText(text);
        return this;
    }

    // set action in custom layout
    public CustomSnackbar setAction(final View.OnClickListener listener) {
        Button actionView = (Button) getView().findViewById(R.id.btn_snackbar);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                // Now dismiss the Snackbar
                //dismiss();
            }
        });
        return this;
    }
}

