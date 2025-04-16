package com.example.bazaregionow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "typStworzen")
public class TypStworzen {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idS")
    private int id;
    @ColumnInfo(name = "nameS")
    private String nazwa;

    @Ignore
    public TypStworzen()
    {
    }

    public TypStworzen(String nazwa) {
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
