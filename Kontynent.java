package com.example.bazaregionow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "kontynenty")
public class Kontynent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idK")
    private int id;
    @ColumnInfo(name = "nameK")
    private String nazwa;

    @Ignore
    public Kontynent()
    {
    }

    public Kontynent(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return  nazwa;
    }
}
