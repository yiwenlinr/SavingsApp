package com.example.savingsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.savingsapp.adapter.SavingAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        nav = findViewById(R.id.nav);
//
//        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.ajustes:
//                        Toast.makeText(MainActivity.this, "Ajustes", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.ahorro:
//                        Toast.makeText(MainActivity.this, "Ahorro", Toast.LENGTH_SHORT).show();
//                        break;
//
//
//                    case R.id.registro:
//                        Toast.makeText(MainActivity.this, "Registro", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    default:
//                }
//                return false;
//            }
//        });


        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MoneyActivity.class));
            }
        });

    }
}