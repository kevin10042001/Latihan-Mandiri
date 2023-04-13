package com.si6b.latihan_mandiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabTambah;
    private RecyclerView rvMahasiswa;
    private MyDatabaseHelper myDB;
    private ArrayList<String> arrId, arrNpm, arrNama, arrProdi;
    private AdapterMahasiswa adapterMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTambah = findViewById(R.id.fab_tambah);
        rvMahasiswa = findViewById(R.id.rv_mahasiswa);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                startActivity(intent);
            }
        });
        myDB = new MyDatabaseHelper(MainActivity.this);
    }

    private void sqLiteToArrayList(){
        Cursor varCursor = myDB.bacaDataMahasiswa();
        if(varCursor.getCount() == 0){
            Toast.makeText(this, "Data Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }
        else{
            while(varCursor.moveToNext()){
                arrId.add(varCursor.getString(0));
                arrNpm.add(varCursor.getString(1));
                arrNama.add(varCursor.getString(2));
                arrProdi.add(varCursor.getString(3));
            }
        }
    }
    private void tampilMahasiswa(){
        arrId = new ArrayList<>();
        arrNpm = new ArrayList<>();
        arrNama = new ArrayList<>();
        arrProdi = new ArrayList<>();

        sqLiteToArrayList();

        //adMahasiswa = new AdapterMahasiswa(MainActivity.this, arrId, arrNpm, arrNama, arrProdi);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvMahasiswa.setAdapter(adapterMahasiswa);


    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilMahasiswa();
    }
}