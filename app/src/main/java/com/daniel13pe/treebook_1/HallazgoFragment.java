package com.daniel13pe.treebook_1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel13pe.treebook_1.model.Montallantas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HallazgoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterMontallantas;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Montallantas> montallantasList;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    String dataTree = "", aux = "";

    public HallazgoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_montallantas,container,false);

        if(getArguments() != null){
            dataTree = getArguments().getString("Tree");
            aux = aux+dataTree;
        }

        recyclerView = itemView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        montallantasList = new ArrayList<>();
        adapterMontallantas = new AdapterMontallantas(montallantasList, R.layout.cardview_detalle,getActivity());
        recyclerView.setAdapter(adapterMontallantas);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Montallantas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                montallantasList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Montallantas montallantas = snapshot.getValue(Montallantas.class);
                        if(montallantas.getNombre().toString().equals(aux)) {
                            montallantasList.add(montallantas);
                        }
                    }
                }
                adapterMontallantas.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return itemView;
    }

}
