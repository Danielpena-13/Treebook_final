package com.daniel13pe.treebook_1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel13pe.treebook_1.model.Recorridos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecorridoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterRecorridos;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Recorridos> recorridosList;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    public RecorridoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_recorrido,container,false);

        recyclerView = itemView.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recorridosList = new ArrayList<>();
        adapterRecorridos = new AdapterRecorridos(recorridosList, R.layout.cardview_recorrido, getActivity());
        recyclerView.setAdapter(adapterRecorridos);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Recorridos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recorridosList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Recorridos recorridos = snapshot.getValue(Recorridos.class);
                        recorridosList.add(recorridos);
                    }
                }
                adapterRecorridos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return itemView;
    }

}
