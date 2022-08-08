package com.example.ontheway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class fuel extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        btn=findViewById(R.id.praci);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(fuel.this,"check next time no service",Toast.LENGTH_LONG).show();
                Toast.makeText(fuel.this,"thank try",Toast.LENGTH_SHORT).show();
            }
        });
    }
}