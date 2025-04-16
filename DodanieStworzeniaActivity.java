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

import com.example.bazaregionow.databinding.ActivityDodanieStworzeniaBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DodanieStworzeniaActivity extends AppCompatActivity {

    ActivityDodanieStworzeniaBinding binding;
    ArrayAdapter<TypStworzen> adapter;
    List<TypStworzen> stworzenia;
    static ObszarDatabase obszarDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDodanieStworzeniaBinding.inflate(getLayoutInflater());
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
        binding.send.setOnClickListener(view1 -> dodaj());
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usun(i);
            }
        });
        binding.powrot.setOnClickListener(view1 -> powrot());
        //wykonaj();
    }

    private void powrot() {
        Intent intent = new Intent(DodanieStworzeniaActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void dodaj() {
        if (!binding.i1.getText().toString().equals("")){
            TypStworzen stworzen;
            stworzen = new TypStworzen(binding.i1.getText().toString());
            boolean powtorzenie = false;
            //sprawdzanie obecności
            for (TypStworzen stworzen1 :
                    stworzenia) {
                if (stworzen.getNazwa().equals(stworzen1.getNazwa()))
                {
                    Toast.makeText(this, "Kontynenty już istnieje w bierzącym kontekście", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    obszarDatabase.zwrocDao().insertS(stworzen);

                    handler.post(
                            new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(DodanieStworzeniaActivity.this, "dodano", Toast.LENGTH_SHORT).show();

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
        obszarDatabase.zwrocDao().deleteS(stworzenia.get(i));
        wczytaj();
        adapter.notifyDataSetChanged();

    }
    void wczytaj()
    {
        stworzenia = new ArrayList<TypStworzen>();
        stworzenia = obszarDatabase.zwrocDao().selectStworzenia();
        adapter = new ArrayAdapter<TypStworzen>(DodanieStworzeniaActivity.this,
                android.R.layout.simple_list_item_1, stworzenia);
        binding.list.setAdapter(adapter);
    }

    private void wykonaj() {
        //obszarDatabase.zwrocDao().insertR(new Region("region2"));
        obszarDatabase.zwrocDao().deleteAtId(2);
        wczytaj();
        adapter.notifyDataSetChanged();


    }


}