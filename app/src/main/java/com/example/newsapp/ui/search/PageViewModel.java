package com.example.newsapp.ui.search;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> mmm=new MutableLiveData<>();
    private MutableLiveData<String> mIndex = new MutableLiveData<String>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(String index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
