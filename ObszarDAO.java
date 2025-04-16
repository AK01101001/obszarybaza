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
    @Insert
    void insertK(Kontynent kontynent);
    @Insert
    void insertS(TypStworzen typStworzen);
    @Delete
    void delete(Obszar obszar);
    @Query("DELETE From regiony Where idR= :id")
    void deleteAtId(int id);
    @Update
    void update(Obszar obszar);
    @Query("Select *, regiony.*, kontynenty.*,typStworzen.* FROM obszary Inner Join regiony On idRegion=regiony.idR Inner JOIN kontynenty On idKontynent=kontynenty.idK inner join typStworzen on idTypStworzen = typStworzen.idS")
    List<ObszarRegion> selectAll();
    @Query("Select * FROM regiony")
    List<Region> selectRegions();
    @Query("Select * FROM kontynenty")
    List<Kontynent> selectKontynents();
    @Query("Select * FROM typStworzen")
    List<TypStworzen> selectStworzenia();
    @Query("Select nameR FROM regiony WHERE idR = :id")
    String selectRegion(int id);
    @Query("Select * FROM obszary WHERE id = :id")
    Obszar selectObszar(int id);
    @Delete
    void deleteR(Region region);
    @Delete
    void deleteK(Kontynent kontynent);
    @Delete
    void deleteS(TypStworzen typStworzen);
}
