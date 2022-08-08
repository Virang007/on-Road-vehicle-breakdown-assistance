package com.example.ontheway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboradUser extends AppCompatActivity {
    TextView t1;
    ImageView tem;
    private FirebaseAuth auth; //connection code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad_user);
        t1=findViewById(R.id.t1);
        tem=findViewById(R.id.singout);
        // call data main activity
        Intent intent = getIntent();
        String str = intent.getStringExtra("em");
        t1.setText(str);
        auth = FirebaseAuth.getInstance();
        tem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });

    }

    public void tips(View view) {
        Intent i =new Intent(DashboradUser.this,help_tips.class);
        startActivity(i);
    }

    public void wallet(View view) {
        Intent i =new Intent(DashboradUser.this,moneyWallet.class);
        startActivity(i);
    }

    public void display(View view) {
        Intent i =new Intent(DashboradUser.this,Display1.class);
        startActivity(i);
    }

    public void fuale(View view) {
        Intent i =new Intent(DashboradUser.this,fuel.class);
        startActivity(i);
    }

    public void about(View view) {
        Intent i =new Intent(DashboradUser.this,about.class);
        startActivity(i);
    }
}