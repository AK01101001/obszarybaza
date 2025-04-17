package com.example.bazaregionow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bazaregionow.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayAdapter<Obszar> adapter;
    ArrayAdapter<Region> adapterR;
    ArrayAdapter<Kontynent> adapterK;
    ArrayAdapter<TypStworzen> adapterS;
    List<ObszarRegion> obszarRegiony;
    List<Obszar> obszary;
    static ObszarDatabase obszarDatabase;
    int id;

    private List<Region> regiony;

    private List<Kontynent> kontynenty;

    private List<TypStworzen> typyStworzen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        id =Integer.parseInt(intent.getStringExtra("id"));
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        obszarDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        ObszarDatabase.class, "kresy_db")
                .addCallback(callback)
                .allowMainThreadQueries()
                .build();
        wczytaj();
        wczytajR();
        wczytajK();
        wczytajS();
        binding.send.setOnClickListener(view1 -> dodaj());
        binding.powrot.setOnClickListener(view1 -> powrot());
        binding.dodajR.setOnClickListener(view1 -> dodanieRegionu());
        binding.dodajK.setOnClickListener(view1 -> dodanieKontynentu());
        binding.dodajS.setOnClickListener(view1 -> dodanieStworzen());

        binding.delete.setOnClickListener(view1 -> usun(id));
        //wykonaj();
    }

    private void powrot() {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
    }

    private void dodanieRegionu() {
        Intent intent = new Intent(MainActivity.this, DodanieRegionuActivity.class);
        startActivity(intent);
    }
    private void dodanieKontynentu() {
        Intent intent = new Intent(MainActivity.this, DodanieRegionuActivity.class);
        startActivity(intent);
    }
    private void dodanieStworzen() {
        Intent intent = new Intent(MainActivity.this, DodanieRegionuActivity.class);
        startActivity(intent);
    }

    private void dodaj() {
        if (!binding.i1.getText().toString().equals("") && !binding.i2.getText().toString().equals("") && !binding.iR.getSelectedItem().toString().equals("")&& !binding.iK.getSelectedItem().toString().equals("") && !binding.iS.getSelectedItem().toString().equals("")) {
            Obszar obszar;
            obszar = new Obszar(binding.i1.getText().toString(), binding.i2.getText().toString(),binding.i4.isChecked(), binding.iR.getSelectedItemPosition()+1,binding.iK.getSelectedItemPosition()+1 ,binding.iS.getSelectedItemPosition()+1  );
            boolean powtorzenie = false;
            //sprawdzanie obecności
            for (ObszarRegion obszar2 :
                    obszarRegiony) {
                if (obszar.getNazwa().equals(obszar2.obszar.getNazwa()))
                {
                    obszar.setId(obszar2.obszar.getId());
                    Toast.makeText(this, "powtorzenie aktualizacja opprzedniego rekordu", Toast.LENGTH_SHORT).show();
                    powtorzenie = true;
                    break;
                }

            }
             final boolean powtorzenie2 = powtorzenie;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (powtorzenie2)
                    {

                        obszarDatabase.zwrocDao().update(obszar);

                    }
                    else
                    {
                        obszarDatabase.zwrocDao().insert(obszar);
                        id = obszar.getId();
                    }

                    handler.post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if (powtorzenie2)
                                    {
                                        Toast.makeText(MainActivity.this, "zmodyfikowano", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "dodano", Toast.LENGTH_SHORT).show();
                                    }
                                    wczytaj();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                    );
                }
            });

        }
    }

    private void usun(int i) {
        obszarDatabase.zwrocDao().delete(obszarRegiony.get(i).obszar);
        id=0;
        powrot();
    }


    private void wczytaj() {

        obszary = new ArrayList<Obszar>();
        obszarRegiony.add(obszarDatabase.zwrocDao().selectObszar(id));
        for (ObszarRegion obszarRegion:
             obszarRegiony) {
            obszary.add(new Obszar(
                    obszarRegion.obszar.getNazwa(),
                    obszarRegion.obszar.getOpis(),

                    obszarRegion.obszar.isBezpieczny(),
                    obszarRegion.region.getNazwa(),
                    obszarRegion.kontynent.getNazwa(),
                    obszarRegion.typStworzen.getNazwa()

            ));

        }
        binding.i1.setText(obszarRegiony.get(0).obszar.getNazwa());
        binding.i2.setText(obszarRegiony.get(0).obszar.getOpis());
        binding.iR.setSelection(obszarRegiony.get(0).region.getId());
        binding.iK.setSelection(obszarRegiony.get(0).kontynent.getId());
        binding.iS.setSelection(obszarRegiony.get(0).typStworzen.getId());
        binding.i4.setChecked(obszarRegiony.get(0).obszar.isBezpieczny());

    }
    void wczytajR()
    {
        regiony = new ArrayList<Region>();
        regiony = obszarDatabase.zwrocDao().selectRegions();
        adapterR = new ArrayAdapter<Region>(MainActivity.this,
                android.R.layout.simple_list_item_1, regiony);
        binding.iR.setAdapter(adapterR);
    }
    void wczytajK()
    {
        kontynenty = new ArrayList<Kontynent>();
        kontynenty = obszarDatabase.zwrocDao().selectKontynents();
        adapterK = new ArrayAdapter<Kontynent>(MainActivity.this,
                android.R.layout.simple_list_item_1, kontynenty);
        binding.iK.setAdapter(adapterK);
    }
    void wczytajS()
    {
        typyStworzen = new ArrayList<TypStworzen>();
        typyStworzen = obszarDatabase.zwrocDao().selectStworzenia();
        adapterS = new ArrayAdapter<TypStworzen>(MainActivity.this,
                android.R.layout.simple_list_item_1, typyStworzen);
        binding.iS.setAdapter(adapterS);
    }

    private void wykonaj() {
        obszarDatabase.zwrocDao().insertR(new Region("region1"));

        obszarDatabase.zwrocDao().insertK(new Kontynent("kontynent1"));
        obszarDatabase.zwrocDao().insertS(new TypStworzen("sworzen1"));

        /*obszarDatabase.zwrocDao().deleteAtId(2);*/
        wczytaj();

        wczytajR();
        wczytajK();
        wczytajS();

        adapter.notifyDataSetChanged();


    }


}