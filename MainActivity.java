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

import com.example.bazaregionow.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayAdapter<Obszar> adapter;
    List<Obszar> obszary;
    ObszarDatabase obszarDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        obszary = new ArrayList<Obszar>();
        RoomDatabase.Callback callback = new RoomDatabase.Callback(){
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
                        ObszarDatabase.class,"kresy_db")
                .addCallback(callback)
                .allowMainThreadQueries()
                .build();
        wczytaj();
        binding.send.setOnClickListener(view1 -> dodaj());
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usun(i);
            }
        });
        wczytaj();

    }

    private void dodaj() {
        if (!binding.i1.getText().toString().equals("") && !binding.i2.getText().toString().equals("") && binding.i3.getSelectedItem().toString() != ""&&!binding.i4.getText().toString().equals("")) {
            Obszar obszar;
            obszar = new Obszar(binding.i1.getText().toString(), binding.i2.getText().toString(), Integer.parseInt(binding.i3.getSelectedItem().toString()), binding.i4.isActivated());

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    obszarDatabase.zwrocDao().insert(obszar);

                    handler.post(
                            new Runnable() {
                                @Override
                                public void run() {

                                }
                            }
                    );
                }
            });
            wczytaj();
            adapter.notifyDataSetChanged();
        }
    }

    private void usun(int i)
    {
        obszarDatabase.zwrocDao().delete(obszary.get(i));
        obszary.remove(i);
        adapter.notifyDataSetChanged();

    }


    private void wczytaj() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                obszary = obszarDatabase.zwrocDao().selectAll();
                handler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ArrayAdapter<Obszar>(MainActivity.this,
                                        android.R.layout.simple_list_item_1,obszary);
                                binding.list.setAdapter(adapter);
                            }
                        }
                );
            }
        });

        //regiony
        executorService = Executors.newSingleThreadExecutor();
        Handler handler2 = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                obszary = obszarDatabase.zwrocDao().selectRegions();
                handler2.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ArrayAdapter<Obszar>(MainActivity.this,
                                        android.R.layout.simple_list_item_1,obszary);
                                binding.list.setAdapter(adapter);
                            }
                        }
                );
            }
        });
    }
}