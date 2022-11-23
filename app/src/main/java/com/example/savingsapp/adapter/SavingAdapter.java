package com.example.savingsapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savingsapp.MoneyActivity;
import com.example.savingsapp.R;
import com.example.savingsapp.model.Saving;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SavingAdapter extends FirestoreRecyclerAdapter<Saving, SavingAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SavingAdapter(@NonNull FirestoreRecyclerOptions<Saving> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int viewType, @NonNull Saving Saving) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.monto.setText(Saving.getMonto());

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MoneyActivity.class);
                i.putExtra("id_money", id);
                activity.startActivity(i);
            }
        });

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteSaving(id);

            }
        });
    }

    private void deleteSaving(String id) {
        mFirestore.collection("savings").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              Toast.makeText(activity, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
           }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show(); }
       });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saving_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monto;
        ImageView btn_delete, btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            monto = itemView.findViewById(R.id.montos);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_edit = itemView.findViewById(R.id.btn_editar);

        }
    }
}
