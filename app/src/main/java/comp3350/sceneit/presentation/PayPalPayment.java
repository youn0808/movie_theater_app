package comp3350.sceneit.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.sceneit.R;

public class PayPalPayment extends AppCompatActivity {


    private TextView totalPrice1;
    private TextView totalPrice2;
    private TextView cardInfo;
    private int price;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        setContentView(R.layout.activity_pay_pal_payment);

        totalPrice1 = (TextView) findViewById(R.id.totalPrice1);
        totalPrice2 = (TextView) findViewById(R.id.totalPrice2);
        cardInfo = (TextView) findViewById(R.id.cardInfo);

        //bundle get the total purchase price
        Bundle b = getIntent().getExtras();
        price = b.getInt("price");

        if(price!=0) {
            changePrice(price);
            editor.putInt("price",price);
            editor.commit();
        }else {
            changePrice(sharedpreferences.getInt("price", 0));
        }

        //get updated card info
        Intent intent = getIntent();
        String car_num = intent.getStringExtra("card_num");
        if (car_num != null){
            cardInfo.setText("Card #: xxxx-" + car_num.substring(12, 16));
        }
    }

    private void changePrice(int price){

        totalPrice1.setText("$" + Integer.toString(price) + ".00");
        totalPrice2.setText("$" + Integer.toString(price) + ".00");
    }

    public void placeOrder(View view) {

        if(!cardInfo.getText().equals("Card #: add card")) {
            Intent intent = new Intent(this, PayPalConfirm.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "You need to add credit card.", Toast.LENGTH_LONG).show();
        }

    }


    public void changePayment(View view) {
        Intent intent = new Intent(this, addCreditCard.class);
        startActivity(intent);
    }


}