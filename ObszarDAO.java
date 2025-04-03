package com.example.bazaregionow;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObszarDAO {
    @Insert
    void insert(Obszar obszar);
    @Delete
    void delete(Obszar obszar);
    @Update
    void update(Obszar obszar);
    @Query("Select * FROM obszary")
    List<Obszar> selectAll();
    @Query("Select * FROM regiony")
    List<Obszar> selectRegions();
}
