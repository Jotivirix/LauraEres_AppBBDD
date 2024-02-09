package com.example.mislentillas_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class lentillas extends AppCompatActivity {
    EditText Caducidad, Graduacion;
    TextView Tipo;
    Spinner Marca;
    Button aceptar_boton , fecha ;
    RadioButton Diaria , Mensual , Anual;
    private int dia, mes, anno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lentillas);
        //añadimos todos los componentes de la interfaz
        Marca = findViewById(R.id.Marca);
        String[]marcas ={"CooperVision","Alcon","BauchandLomb"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,marcas);
        Marca.setAdapter(adapter);
        //creamos los radio button junto con su textview
       Tipo=findViewById(R.id.Tipo);
        Caducidad = findViewById(R.id.Caducidad);
        Graduacion = findViewById(R.id.Graduacion);
        fecha= findViewById(R.id.fecha);
        //Creamos el boton para el caendario
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==fecha){
                    final Calendar c = Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    anno=c.get(Calendar.YEAR);
                    //A partir de los strings que hemos creado cogemos el año , dia y mes y con el datepickerdialagog lo almacenamos
                    DatePickerDialog datePickerDialog = new DatePickerDialog(lentillas.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        //creamos un metodo para añadir a caducidad los datos obtenidos del calendario
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Caducidad.setText(dayOfMonth+"/"+ (month+1)+"/"+year);
                        }
                    }
                            ,dia , mes, anno);
                    datePickerDialog.show();
                }
            }
        });
        Diaria=(RadioButton) findViewById(R.id.Diaria);
        Mensual=(RadioButton)findViewById(R.id.Mensual);
        Anual = (RadioButton)findViewById(R.id.Anual);

        //creamos un clicklistener para poder editar las opciones de los radiobutton
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Opcionselecionada="";
          if(Diaria.isChecked()==true){
              Opcionselecionada+="Diaria";

                }
          if(Mensual.isChecked()==true){
              Opcionselecionada+="Mensual";
          }
              if(Anual.isChecked()==true){
                    Opcionselecionada+="Anual";
                }
            Tipo.setText(Opcionselecionada);
            }
        };
 Diaria.setOnClickListener(list);
 Mensual.setOnClickListener(list);
 Anual.setOnClickListener(list);
        aceptar_boton = findViewById(R.id.aceptar_boton);

        aceptar_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(lentillas.this);
                mydb.addDatos( Marca.getSelectedItem().toString().trim(),Tipo.getText().toString().trim(), Caducidad.getText().toString().trim(),Graduacion.getText().toString().trim());
                finish();

            }
        });
    }
}