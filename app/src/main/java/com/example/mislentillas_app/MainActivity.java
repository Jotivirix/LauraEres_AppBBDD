package com.example.mislentillas_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Creamos las clases RecyclerView y el boton flotante
    RecyclerView recyclerView;
    FloatingActionButton add_button, actualizar_btn;
    MyDatabaseHelper myDB;
    ImageView lentilla;
    CustomAdapter customAdapter;
    ArrayList<String> ID, Marca, Tipo, Caducidad, Graduacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //conectamos las variables con los objetos añadidos en la interfaz
        recyclerView = findViewById(R.id.recylcerView);

        actualizar_btn = findViewById(R.id.actualizar_btn);
        add_button = findViewById(R.id.add_button);

        lentilla = findViewById(R.id.lentilla);
        actualizar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Unimos las dos pantallas mendiante el boton añadir con un intent
                Intent intent = new Intent(MainActivity.this, Lentillas.class);
                startActivity(intent);

            }
        });
        //inicializamos la base de datos

        myDB = new MyDatabaseHelper(MainActivity.this);
        ID = new ArrayList<>();
        Marca = new ArrayList<>();
        Tipo = new ArrayList<>();
        Caducidad = new ArrayList<>();
        Graduacion = new ArrayList<>();
        crearArray();

        //gracias a customadapter, cogemos los datos de los arrays y los añadimos a nuestra recycleview para que los podamos ver en la interfaz
        customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, ID, Marca, Tipo, Caducidad, Graduacion);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private void Actualizar() {
        ID.clear();
        Marca.clear();
        Tipo.clear();
        Caducidad.clear();
        Graduacion.clear();
        crearArray();

        // Notifica al adaptador que los datos han cambiado
        customAdapter.notifyDataSetChanged();
    }

    void crearArray() {
        Cursor cursor = myDB.leerDatos();
        if (cursor.getCount() == 0) {
            lentilla.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                ID.add(cursor.getString(0));
                Marca.add(cursor.getString(1));
                Tipo.add(cursor.getString(2));
                Caducidad.add(cursor.getString(3));
                Graduacion.add(String.valueOf(cursor.getString(4)));
            }
            lentilla.setVisibility(View.GONE);
        }
    }

}