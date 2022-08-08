package com.example.ontheway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class moneyWallet extends AppCompatActivity  implements PaymentResultListener {
    EditText recha;
    TextView balans;
    Button re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_wallet);

        recha=findViewById(R.id.recha);
//        balans=findViewById(R.id.balans);
        re=findViewById(R.id.re);

//        re.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = recha.getText().toString();
//
//                if (!str.isEmpty())
//                {
//                    balans.setText(str);
//                }
//                else {
//                    Toast.makeText(moneyWallet.this,"plz Enter amount",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        // adding on click listener to our button.
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // amount that is entered by user.
                String samount = recha.getText().toString();

                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_mwTYkpZFrzAaEU");

                // set image
                checkout.setImage(R.drawable.ncar);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "OnTheWay");

                    // put description
                    object.put("description", "Test payment");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                    object.put("prefill.contact", "9512956095");

                    // put email
                    object.put("prefill.email", "acharyavirang96@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(moneyWallet.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
          // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}