package com.example.mislentillas_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //Declaramos a variable Context
    private Context context;
    //declaramos los componentes de la base de datos
    public static final String DATABASE_NAME="Mislentillas.db";
    private static final int DATABASE_VERSION =1 ;
    private static final String TABLE_NAME ="mis_lentillas";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_MARCA="marca";

    private static final String COLUMN_TIPO="tipo";
    private static final String COLUMN_CADUCIDAD="caducidad";
    private static final String COLUMN_GRADUACION="graduacion";
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Aqui vamos a crear la bse de datos

        String query ="CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MARCA + " TEXT, " + COLUMN_TIPO+ " TEXT, " + COLUMN_CADUCIDAD + " TEXT, " + COLUMN_GRADUACION + " TEXT); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //borraremos la tabla si existe ya
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //creamos la tabla
        onCreate(db);


    }
    void addDatos(String marca , String tipo , String caducidad , String graduacion){
        //Creamos un objeto con propiedades de base de datos que se pueda sobreescribir
        SQLiteDatabase db= this.getWritableDatabase();
        //en contenvalues vamos a guardar todos los datos de nuestra base de datos

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MARCA,marca);
        cv.put(COLUMN_TIPO,tipo);
        cv.put(COLUMN_CADUCIDAD,caducidad);
        cv.put(COLUMN_GRADUACION,graduacion);

        //Insertamos en la tabla esos valores
        long result= db.insert(TABLE_NAME, null , cv);
      //Creamos unos mensajes en la aplicacion si esta funcionando o no el metodo
        if(result==-1){
            Toast.makeText(context,"ERROR",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Datos añadidos",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor leerDatos(){
        //Creamos un metodo para poder ller la base de datos e importarla en el ejercicio principal
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        //creamos un cursor para contener todos los datos
        Cursor cursor = null;
        if(db!=null){
            cursor= db.rawQuery(query,null);
        }
        return cursor;
    }
    void editarDatos(String row_ID ,String Marca , String Tipo , String Caducidad, String Graduacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MARCA, Marca);
        cv.put(COLUMN_TIPO, Tipo);
        cv.put(COLUMN_CADUCIDAD, Caducidad);
        cv.put(COLUMN_GRADUACION, Graduacion);


        long result = db.update(TABLE_NAME, cv,  COLUMN_ID + "=?", new String[]{row_ID});

        if(result==-1){
            Toast.makeText(context,"Error de edicion",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Editado correctamente",Toast.LENGTH_SHORT).show();
        }

    }
    void BorrarDatos(String row_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,COLUMN_ID + "=?",new String[]{row_ID});
        if(result ==-1){
            Toast.makeText(context,"Error al borrar los datos",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Borrado exitosamente",Toast.LENGTH_SHORT).show();
        }
    }

    }

