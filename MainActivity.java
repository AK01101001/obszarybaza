package com.example.bazaregionow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
    ArrayAdapter<Region> adapter2;
    List<ObszarRegion> obszarRegiony;
    List<Obszar> obszary;
    static ObszarDatabase obszarDatabase;
    private List<Region> regiony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
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
        binding.send.setOnClickListener(view1 -> dodaj());
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usun(i);
            }
        });
        //wykonaj();
    }

    private void dodaj() {
        if (!binding.i1.getText().toString().equals("") && !binding.i2.getText().toString().equals("") && !binding.i3.getSelectedItem().toString().equals("")) {
            Obszar obszar;
            obszar = new Obszar(binding.i1.getText().toString(), binding.i2.getText().toString(), binding.i3.getSelectedItemPosition()+1, binding.i4.isChecked() );
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
        wczytaj();
        adapter.notifyDataSetChanged();

    }


    private void wczytaj() {

        obszary = new ArrayList<Obszar>();
        obszarRegiony = obszarDatabase.zwrocDao().selectAll();
        for (ObszarRegion obszarRegion:
             obszarRegiony) {
            obszary.add(new Obszar(
                    obszarRegion.obszar.getNazwa(),
                    obszarRegion.obszar.getOpis(),
                    obszarRegion.region.getNazwa(),
                    obszarRegion.obszar.isBezpieczny()

            ));

        }
        adapter = new ArrayAdapter<Obszar>(MainActivity.this,
                android.R.layout.simple_list_item_1, obszary);
        binding.list.setAdapter(adapter);



    }
    void wczytajR()
    {
        regiony = new ArrayList<Region>();
        regiony = obszarDatabase.zwrocDao().selectRegions();
        adapter2 = new ArrayAdapter<Region>(MainActivity.this,
                android.R.layout.simple_list_item_1, regiony);
        binding.i3.setAdapter(adapter2);
    }

    private void wykonaj() {
        //obszarDatabase.zwrocDao().insertR(new Region("region2"));
        obszarDatabase.zwrocDao().deleteAtId(2);
        wczytaj();
        adapter.notifyDataSetChanged();


    }


}