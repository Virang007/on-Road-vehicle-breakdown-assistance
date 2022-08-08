package com.example.ontheway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

public class macDashbord extends AppCompatActivity {
    TextView t1,delet,update,l;
    private FirebaseAuth auth; //connection code
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_dashbord);

        t1=findViewById(R.id.t1);
        delet=findViewById(R.id.delet);
        update=findViewById(R.id.update);
        l=findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // call data main activity
        Intent intent = getIntent();
        String str = intent.getStringExtra("em");
        t1.setText(str);
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String st1 = t1.getText().toString();
                Intent intent=new Intent(macDashbord.this,up_date.class);
                intent.putExtra("Em",st1);
                startActivity(intent);


            }
        });
        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("student").whereEqualTo("email",str).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressDialog =new ProgressDialog(macDashbord.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setTitle("Delete data....");
                        progressDialog.show();
                        WriteBatch b =FirebaseFirestore.getInstance().batch();
                        List<DocumentSnapshot> s=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot:s)
                        {
                            b.delete(documentSnapshot.getReference());
                        }
                        b.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(macDashbord.this,"sucess",Toast.LENGTH_SHORT).show();
                                progressDialog.setTitle("witing");
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(macDashbord.this,"fall",Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });

                    }
                });

            }
        });
    }

    public void veryfy(View view) {
        Intent intent =new Intent(macDashbord.this,mac_register.class);
        startActivity(intent);

    }
}