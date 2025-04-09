package com.example.bazaregionow;

import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity(tableName = "obszary",
        foreignKeys ={
        @ForeignKey(
                entity = Region.class,
                parentColumns ="idR",
                childColumns = "idRegion"
        )
        }
)
public class Obszar{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "nazwa")
    private String nazwa;
    @ColumnInfo(name = "opis")
    private String opis;
    @ColumnInfo(name = "idRegion")
    private int idRegion;
    @ColumnInfo(name = "bezpieczny")
    private boolean bezpieczny;
    @Ignore
    private String Region;



    @Ignore
    public Obszar() {
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public Obszar(String nazwa, String opis, int idRegion, boolean bezpieczny) {
        this.id = 0;
        this.nazwa = nazwa;
        this.opis = opis;
        this.idRegion = idRegion;
        this.bezpieczny = bezpieczny;
    }
    @Ignore
    public Obszar(String nazwa, String opis, String Region, boolean bezpieczny) {
        this.id = 0;
        this.nazwa = nazwa;
        this.opis = opis;
        this.Region = Region;
        this.bezpieczny = bezpieczny;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }


    public boolean isBezpieczny() {
        return bezpieczny;
    }

    public void setBezpieczny(boolean bezpieczny) {
        this.bezpieczny = bezpieczny;
    }

    @Override
    public String toString() {
        return "Obszar{" +
                "nazwa='" + nazwa + '\'' +
                ", opis='" + opis + '\'' +
                ", idRegion=" + Region /*wczytajR()*/ +
                ", bezpieczny=" + bezpieczny +
                '}';
    }

    private String wczytajR() {
                String output  = MainActivity.obszarDatabase.zwrocDao().selectRegion(idRegion);
        return  output;
    }
}
