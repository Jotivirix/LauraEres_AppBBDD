package com.example.mislentillas_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Activity_update extends AppCompatActivity {
    EditText Caducidad2, Graduacion2;
    Button Editar, Borrar, fecha2;
    TextView Tipo2;
    RadioButton Diaria, Mensual, Anual;
    Spinner Marca2;
    private int dia, mes, anno;
    String originalID, originalMarca, originalTipo, originalCaducidad, originalGraduacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if (getIntent().hasExtra("ID") && getIntent().hasExtra("Marca") && getIntent().hasExtra("Tipo") && getIntent().hasExtra("Caducidad") && getIntent().hasExtra("Graduacion")) {
            //Cogemos los datos del intetnt
            originalID = getIntent().getStringExtra("ID");
            originalMarca = getIntent().getStringExtra("Marca");
            originalTipo = getIntent().getStringExtra("Tipo");
            originalCaducidad = getIntent().getStringExtra("Caducidad");
            originalGraduacion = getIntent().getStringExtra("Graduacion");
        } else {
            Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show();
        }

        Marca2 = findViewById(R.id.marca2);
        Tipo2 = findViewById(R.id.tipoLabel);
        Diaria = (RadioButton) findViewById(R.id.diaria2Button);
        Mensual = (RadioButton) findViewById(R.id.mensual2Button);
        Anual = (RadioButton) findViewById(R.id.anual2Button);
        Caducidad2 = findViewById(R.id.caducidad2);
        Graduacion2 = findViewById(R.id.graduacion2);

        String[] marcas = {"CooperVision", "Alcon", "BauchandLomb"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, marcas);

        Marca2.setAdapter(adapter);

        if(originalMarca.equals("CooperVision")){
            Marca2.setSelection(0);
        }
        else if(originalMarca.equals("Alcon")){
            Marca2.setSelection(1);
        }
        else{
            Marca2.setSelection(2);
        }

        //Set values to screen
        if(originalTipo.equals("Diaria")){
            Diaria.setChecked(true);
            Mensual.setChecked(false);
            Anual.setChecked(false);
        }
        else if(originalTipo.equals("Mensual")){
            Diaria.setChecked(false);
            Mensual.setChecked(true);
            Anual.setChecked(false);
        }
        else if(originalTipo.equals("Anual")){
            Diaria.setChecked(false);
            Mensual.setChecked(false);
            Anual.setChecked(true);
        }

        Caducidad2.setText(originalCaducidad);
        Graduacion2.setText(originalGraduacion);

        //creamos un clicklistener para poder editar las opciones de los radiobutton
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Opcionselecionada = "";
                if (Diaria.isChecked() == true) {
                    Opcionselecionada += "Diaria";
                }
                if (Mensual.isChecked() == true) {
                    Opcionselecionada += "Mensual";
                }
                if (Anual.isChecked() == true) {
                    Opcionselecionada += "Anual";
                }
                Tipo2.setText(Opcionselecionada);
            }
        };

        Diaria.setOnClickListener(list);
        Mensual.setOnClickListener(list);
        Anual.setOnClickListener(list);

        Editar = findViewById(R.id.editar);
        Editar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(Activity_update.this);

                String nuevaMarca = Marca2.getSelectedItem().toString().trim();
                String nuevoTipo = Tipo2.getText().toString().trim();
                String nuevaCaducidad = Caducidad2.getText().toString().trim();
                String nuevaGraduacion = Graduacion2.getText().toString().trim();

                //  Pasar los nuevos datos al método editarDatos
                mydb.editarDatos(originalID, nuevaMarca, nuevoTipo, nuevaCaducidad, nuevaGraduacion);
                finish();
            }
        });

        Borrar = findViewById(R.id.borrar);
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoconfirmar();
            }
        });

        fecha2 = findViewById(R.id.fecha2);
        fecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == fecha2) {
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    anno = c.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_update.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Caducidad2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }
                            , dia, mes, anno);
                    datePickerDialog.show();
                }
            }
        });
    }

    void dialogoconfirmar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Borrar " + originalID + " ?");
        builder.setMessage("¿Estas seguro que quieres borrar " + originalID + " ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(Activity_update.this);
                mydb.BorrarDatos(originalID);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}