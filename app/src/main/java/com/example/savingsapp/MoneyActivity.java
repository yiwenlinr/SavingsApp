package com.example.savingsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savingsapp.adapter.SavingAdapter;
import com.example.savingsapp.model.Saving;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MoneyActivity extends AppCompatActivity {

    Button btn_add2;
    EditText monto;
    FirebaseFirestore mFirestore;
    RecyclerView mRecycler;
    SavingAdapter mAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_money);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("savings");

        FirestoreRecyclerOptions<Saving> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Saving>().setQuery(query, Saving.class).build();

        mAdapter = new SavingAdapter(firestoreRecyclerOptions,this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        this.setTitle("Empieza a Ahorrar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_money");

        mFirestore = FirebaseFirestore.getInstance();

        monto = findViewById(R.id.dinero);
        btn_add2 = findViewById(R.id.btn_add2);

        if (id == null || id == ""){
            btn_add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ingresodinero = monto.getText().toString().trim();
                    if(ingresodinero.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar monto", Toast.LENGTH_SHORT).show();
                    }else{
                        postMonto(ingresodinero);
                    }

                }
            });
        }else{
            btn_add2.setText("actualizar");
            getMonto(id);
            btn_add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ingresodinero = monto.getText().toString().trim();

                    if(ingresodinero.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar monto", Toast.LENGTH_SHORT).show();
                    }else{
                        updateMonto(ingresodinero, id);
                    }
                }
            });
        }

        }

    private void updateMonto(String ingresodinero, String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("monto", ingresodinero);

        mFirestore.collection("savings").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void postMonto(String ingresodinero) {
        Map<String,Object> map = new HashMap<>();
        map.put("monto", ingresodinero);


       mFirestore.collection("savings").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Agregado Exitosamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getMonto(String id){
        mFirestore.collection("savings").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String ingresodinero = documentSnapshot.getString("monto");
                monto.setText(ingresodinero);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });
    }





    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
