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

import com.example.bazaregionow.databinding.ActivityListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListActivity extends AppCompatActivity {

    ActivityListBinding binding;
    ArrayAdapter<Obszar> adapter;
    List<ObszarRegion> obszarRegiony;
    List<Obszar> obszary;
    static ObszarDatabase obszarDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
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
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                przejście(obszarRegiony.get(i).obszar.getId());
            }
        });



        //wykonaj();
    }

    private void przejście(int i) {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.putExtra("id",i);
        startActivity(intent);
    }


    private void wczytaj() {

        obszary = new ArrayList<Obszar>();
        obszarRegiony = obszarDatabase.zwrocDao().selectAll();
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
            adapter = new ArrayAdapter<Obszar>(ListActivity.this,
                    android.R.layout.simple_list_item_1, obszary);
            binding.list.setAdapter(adapter);

        }

    }

    private void wykonaj() {
        obszarDatabase.zwrocDao().insertR(new Region("region1"));

        obszarDatabase.zwrocDao().insertK(new Kontynent("kontynent1"));
        obszarDatabase.zwrocDao().insertS(new TypStworzen("sworzen1"));

        /*obszarDatabase.zwrocDao().deleteAtId(2);*/
        wczytaj();


        adapter.notifyDataSetChanged();


    }


}