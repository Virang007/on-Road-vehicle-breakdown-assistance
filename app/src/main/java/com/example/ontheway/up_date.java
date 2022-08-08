package com.example.ontheway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class up_date extends AppCompatActivity{
    private EditText name;
    private EditText content;
    private EditText city;
    private EditText district;
    private EditText skill;
    private EditText email;
    private Button btn;

    //Location code
    EditText latitudeTextView, longitTextView;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    //...........

    private FirebaseFirestore db; //connection code
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date);
        name=findViewById(R.id.name);
        content=findViewById(R.id.contect);
        city=findViewById(R.id.city);
        district=findViewById(R.id.district);
        skill=findViewById(R.id.skill);
        email=findViewById(R.id.email);
        btn=findViewById(R.id.btn_regi);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String str = intent.getStringExtra("Em");
        email.setText(str);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name1 = name.getText().toString();
////
//                String email1 = email.getText().toString();
                progressDialog =new ProgressDialog(up_date.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("update data...");
                progressDialog.show();
                setup();
            }
        });
    }

    private void setup() {
        String email1 = email.getText().toString();
        Query query=db.collection("student").whereEqualTo("email",email1);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                StringBuilder builder =new StringBuilder();
                for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                {
                    String id =doc.getId();
                    update(id);
                }
            }
        });
    }

    private void update(String id) {
        String name1 = name.getText().toString();
        String content1 = content.getText().toString();
        String city1 = city.getText().toString();
        String district1 = district.getText().toString();
        String skill1 = skill.getText().toString();

        if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(content1) || TextUtils.isEmpty(city1) || TextUtils.isEmpty(district1) ||
                TextUtils.isEmpty(skill1))
        {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(up_date.this,"Empty field Are not Allowed",Toast.LENGTH_SHORT).show();

        }
        else if (content.getText().toString().length()<10 || content1.length()>10)
        {

            // am_checked=0;
            Toast.makeText(up_date.this,"Enter correct Contect",Toast.LENGTH_SHORT).show();
        }
        else {
            db.collection("student").document(id).update("name",name1,"contact",content1,"city",city1,"district",district1,"skill",skill1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(up_date.this, "Successfully updated..", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(up_date.this, "faille updated..", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });}
    }
}