package com.example.newsapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ForYouDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteForYou favoriteForYou);

    @Delete
    void delete(FavoriteForYou favoriteForYou);

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteforyou WHERE id=:id)")
    int isFavorite(int id);

    @Query("SELECT COUNT(*) FROM favoriteforyou")
    int getCount();

    @Query("select * from favoriteforyou")
    List<FavoriteForYou> getFavoriteForYou();





  /*  @Update
    int getUpdatedCount(FavoriteForYou favoriteForYou);*/




  /*  @Query("SELECT * FROM favoriteforyou WHERE favoriteforyou_sectionName=:asd ")
    String isString(String asd);*/
}
