package com.example.bazaregionow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "regiony")
public class Region {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idR")
    private int id;
    @ColumnInfo(name = "name")
    private String nazwa;

    @Ignore
    public Region()
    {
    }

    public Region(String nazwa) {
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
