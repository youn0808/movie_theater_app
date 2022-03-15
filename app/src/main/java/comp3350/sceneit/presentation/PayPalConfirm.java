package comp3350.sceneit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.sceneit.R;

public class PayPalConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_confirm);
    }

    public void goHomePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}