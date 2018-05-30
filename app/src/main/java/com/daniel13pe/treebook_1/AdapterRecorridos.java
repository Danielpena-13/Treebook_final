package com.daniel13pe.treebook_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel13pe.treebook_1.model.Montallantas;
import com.daniel13pe.treebook_1.model.Recorridos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecorridos extends RecyclerView.Adapter<AdapterRecorridos.RecorridosViewHolder>{

    ArrayList<Recorridos> recorridos = new ArrayList<Recorridos>();
    private ArrayList<Recorridos> recorridosList;
    private int resource;
    private Activity activity;

    public AdapterRecorridos(ArrayList<Recorridos> recorridosList) {
        this.recorridosList = recorridosList;
    }

    public AdapterRecorridos(ArrayList<Recorridos> recorridosList,int resource, Activity activity) {
        this.recorridosList = recorridosList;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecorridosViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType ) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Para más detalle, Manten Presionado!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RecorridosViewHolder recorridosViewHolder2 = new RecorridosViewHolder(itemView);

                Intent intent = new Intent(activity, MapDetalles.class);
                intent.putExtra("Inicio", recorridosViewHolder2.tInicio.getText().toString());
                intent.putExtra("Fin", recorridosViewHolder2.tFin.getText().toString());
                activity.startActivity(intent);
                return false;
            }
        });
        return new RecorridosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecorridosViewHolder holder, int position) {
        Recorridos recorridos = recorridosList.get(position);
        holder.bindRecorridos(recorridos);

    }

    @Override
    public int getItemCount() {
        return recorridosList.size();
    }

    public  class RecorridosViewHolder extends RecyclerView.ViewHolder{

        private TextView tInicio, tFin, tFecha;
        private ImageView iFoto;
        private Activity activity;
        ArrayList<Recorridos> recorridos = new ArrayList<Recorridos>();
        Context ctx;

        public RecorridosViewHolder(View itemView) {
            super(itemView);
            tInicio = itemView.findViewById(R.id.tInicio);
            tFin = itemView.findViewById(R.id.tFin);
            tFecha = itemView.findViewById(R.id.tFecha);
            iFoto = itemView.findViewById(R.id.iFoto);

        }

        public void bindRecorridos(Recorridos recorridos) {
            tInicio.setText(recorridos.getInicio());
            tFin.setText(recorridos.getFin());
            Picasso.get().load(recorridos.getFoto()).into(iFoto);
            tFecha.setText(recorridos.getFecha());
        }
    }

}

