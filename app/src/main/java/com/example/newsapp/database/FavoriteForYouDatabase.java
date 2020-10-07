package com.example.newsapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteForYou.class},version = 3)
public abstract class FavoriteForYouDatabase extends RoomDatabase{
    public abstract ForYouDao forYouDao();
}
