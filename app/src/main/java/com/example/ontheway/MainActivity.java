package com.example.ontheway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView textView,forg;
    String eforg;
    private Button signUp;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.select);
        signUp=findViewById(R.id.signUp);
        // enter values Edit text
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        auth =FirebaseAuth.getInstance();
        forg=findViewById(R.id.forg);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialise the alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Register");
                builder.setIcon(R.drawable.chose);
                builder.setCancelable(false);
                // handle the positive button of the dialog
                builder.setPositiveButton("User Register", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(MainActivity.this,user_register.class);
                        startActivity(intent);

                    }
                });

                // handle the negative button of the alert dialog
                builder.setNegativeButton("Mechanic Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(MainActivity.this,mca_login.class);
                        startActivity(intent);
                    }
                });

                // handle the neutral button of the dialog to clear
                // the selected items boolean checkedItem
                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // create the builder
                builder.create();

                // create the alert dialog with the
                // alert dialog builder instance
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1 =email.getText().toString();
                String password1= password.getText().toString();
//
                if (!email1.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email1).matches())
                {

                    if (!password1.isEmpty())
                    {
                        progressDialog =new ProgressDialog(MainActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setTitle("Login...");
                        progressDialog.show();
                        login(email1,password1);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Empty field Are not Allowed",Toast.LENGTH_SHORT).show();
                    }

                }
                else if (email1.isEmpty()){
                    Toast.makeText(MainActivity.this,"Empty field Are not Allowed",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Enter correct Email",Toast.LENGTH_SHORT).show();

                }
            }
        });
        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder di= new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                di.setTitle("Enter Email and forgetPassword");
                EditText editText=new EditText(MainActivity.this);

                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                di.setView(editText);
                di.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eforg = editText.getText().toString();

                        if (eforg.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"email not match",Toast.LENGTH_SHORT).show();
                        }else {
                            forget();
                        }


                    }
                });
                di.show();
            }
        });
    }

    private void forget() {
        auth.sendPasswordResetEmail(eforg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"check you email id",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String email1, String password1) {
        auth.signInWithEmailAndPassword(email1,password1).addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String str = email.getText().toString();
                Intent intent =new Intent(MainActivity.this,DashboradUser.class);
                intent.putExtra("em",str);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Login SuccessFully.",Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Enter correct Password.",Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}