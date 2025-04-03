package com.example.bazaregionow;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Obszar.class},version = 1)
public abstract class ObszarDatabase extends RoomDatabase {
    public abstract ObszarDAO zwrocDao();
}
