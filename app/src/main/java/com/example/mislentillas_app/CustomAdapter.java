package com.example.mislentillas_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList ID,Marca , Tipo , Caducidad, Graduacion;
    Animation animacion;

    CustomAdapter(Activity activity, Context context , ArrayList ID, ArrayList Marca , ArrayList Tipo , ArrayList Caducidad , ArrayList Graduacion  ){
        this.activity=activity;
        this.context=context;
        this.ID=ID;
        this.Marca=Marca;
        this.Tipo=Tipo;
        this.Caducidad=Caducidad;
        this.Graduacion=Graduacion;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.mi_columna,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.ID.setText(String.valueOf(ID.get(position)));
        holder.Marca_txt.setText(String.valueOf(Marca.get(position)));
        holder.Tipo_txt.setText(String.valueOf(Tipo.get(position)));
        holder.Caducidad_txt.setText(String.valueOf(Caducidad.get(position)));
        holder.Graduacion_txt.setText(String.valueOf(Graduacion.get(position)));
        //con el linear layout hacemos que segund la posicion donde clickemos podamos acceder a los datos añadididos y editarlos
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override

            //cuando cliquemos en los datos nos dejara modificarlos a los nuevos
            public void onClick(View v) {;
                Intent intent = new Intent(context, Activity_update.class);
                intent.putExtra("ID",String.valueOf(ID.get(position)));
                intent.putExtra("Marca",String.valueOf(Marca.get(position)));
                intent.putExtra("Tipo",String.valueOf(Tipo.get(position)));
                intent.putExtra("Caducidad",String.valueOf(Caducidad.get(position)));
                intent.putExtra("Graduacion",String.valueOf(Graduacion.get(position)));
                activity.startActivityForResult(intent,1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID, Marca_txt , Tipo_txt , Caducidad_txt , Graduacion_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            Marca_txt = itemView.findViewById(R.id.Marca_txt);
            Tipo_txt = itemView.findViewById(R.id.Tipo_txt);
            Caducidad_txt = itemView.findViewById(R.id.Caducidad_txt);
            Graduacion_txt = itemView.findViewById(R.id.Graduacion_txt);
            mainLayout=itemView.findViewById(R.id.mainLayout);
            //Animacion Recyclerview
            animacion = AnimationUtils.loadAnimation(context, R.anim.adaptardor_anim);
            mainLayout.setAnimation(animacion);
        }
    }
}
