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
        ),
        @ForeignKey(
                entity = Kontynent.class,
                parentColumns ="idK",
                childColumns = "idKontynent"
        ),
        @ForeignKey(
                entity = TypStworzen.class,
                parentColumns ="idS",
                childColumns = "idTypStworzen"
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
    @ColumnInfo(name = "idKontynent")
    private int idKontynentu;
    @ColumnInfo(name = "idTypStworzen")
    private int idTypStworzen;
    @ColumnInfo(name = "bezpieczny")
    private boolean bezpieczny;
    @Ignore
    private String Region;
    @Ignore
    private String Kontynent;
    @Ignore
    private String TypStworzen;



    @Ignore
    public Obszar() {
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public Obszar(String nazwa, String opis, boolean bezpieczny, int idRegion, int idKontynentu, int idTypStworzen) {
        this.id = 0;
        this.nazwa = nazwa;
        this.opis = opis;
        this.idRegion = idRegion;
        this.idKontynentu = idKontynentu;
        this.idTypStworzen = idTypStworzen;
        this.bezpieczny = bezpieczny;
    }

    @Ignore
    public Obszar(String nazwa, String opis, boolean bezpieczny, String region, String kontynent, String typStworzen) {
        this.id = 0;
        this.nazwa = nazwa;
        this.opis = opis;
        this.bezpieczny = bezpieczny;
        Region = region;
        Kontynent = kontynent;
        TypStworzen = typStworzen;
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

    public int getIdKontynentu() {
        return idKontynentu;
    }

    public void setIdKontynentu(int idKontynentu) {
        this.idKontynentu = idKontynentu;
    }

    public int getIdTypStworzen() {
        return idTypStworzen;
    }

    public void setIdTypStworzen(int idTypStworzen) {
        this.idTypStworzen = idTypStworzen;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getKontynent() {
        return Kontynent;
    }

    public void setKontynent(String kontynent) {
        Kontynent = kontynent;
    }

    public String getTypStworzen() {
        return TypStworzen;
    }

    public void setTypStworzen(String typStworzen) {
        TypStworzen = typStworzen;
    }

    @Override
    public String toString() {
        return  nazwa + '-' + Region+ '-' + Kontynent;
    }

    public String wypiszWszystko() {
        return "Obszar{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                ", opis='" + opis + '\'' +
                ", idRegion=" + idRegion +
                ", bezpieczny=" + bezpieczny +
                ", Region='" + Region + '\'' +
                '}';
    }
}
