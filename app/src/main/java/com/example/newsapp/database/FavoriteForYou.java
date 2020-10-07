package com.example.newsapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoriteforyou")
public class FavoriteForYou {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "favoriteforyou_sectionName")
    String favoriteSectionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavoriteSectionName() {
        return favoriteSectionName;
    }

    public void setFavoriteSectionName(String favoriteSectionName) {
        this.favoriteSectionName = favoriteSectionName;
    }
}

