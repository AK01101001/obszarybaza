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
    @Insert
    void insertR(Region region);
    @Delete
    void delete(Obszar obszar);
    @Query("DELETE From regiony Where idR= :id")
    void deleteAtId(int id);
    @Update
    void update(Obszar obszar);
    @Query("Select *, regiony.* FROM obszary Inner Join regiony On idRegion=regiony.idR")
    List<ObszarRegion> selectAll();
    @Query("Select * FROM regiony")
    List<Region> selectRegions();
    @Query("Select name FROM regiony WHERE idR = :id")
    String selectRegion(int id);
}
