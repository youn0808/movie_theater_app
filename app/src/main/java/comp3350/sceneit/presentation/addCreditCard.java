package comp3350.sceneit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import comp3350.sceneit.logic.CreditManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import comp3350.sceneit.R;


public class addCreditCard extends AppCompatActivity {
    private TextView card_name;
    private TextView card_num;
    private TextView card_exp_date;
    private TextView card_cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        card_name = (EditText) findViewById(R.id.text_input_card_name);
        card_num = (EditText) findViewById(R.id.text_input_card_number);
        card_exp_date = (EditText) findViewById(R.id.text_input_exp_date);
        card_cvv = (EditText) findViewById(R.id.text_input_card_cvv);

        card_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateCardName();
                }
            }
        });
        card_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateCardNum();
                }
            }
        });
        card_exp_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateExpDate();
                }
            }
        });
        card_cvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateCVV();
                }
            }
        });
    }

    //check for correct credit card name
    private boolean validateCardName() {
        String card_name_input = card_name.getText().toString().trim();
        boolean result = false;
        if (card_name_input.isEmpty()) {
            card_name.setError("Field can't be empty");
        } else {
            card_name.setError(null);
            result = true;
        }
        return result;
    }

    //check for correct credit card length
    private boolean validateCardNum() {
        String card_num_input = card_num.getText().toString().trim();
        boolean result = false;
        if (card_num_input.isEmpty()) {
            card_num.setError("Field can't be empty");
        } else if (card_num_input.length() < 16 || !CreditManager.validateCredit(card_num.getText().toString())) {
            card_num.setError("Card number is not valid");
        }
        else{
            card_num.setError(null);
            result = true;
        }
        return result;
    }

    //check validate Expiration date
    private boolean validateExpDate() {
        String card_exp_date_input = card_exp_date.getText().toString().trim();
        boolean result = false;
        try {
            String[] checkDate = card_exp_date_input.split("/");
            int month = Integer.parseInt(checkDate[0]);
            int year = Integer.parseInt(checkDate[1]) + 2000;
            Calendar cal = Calendar.getInstance();

            if (card_exp_date_input.isEmpty()) {
                card_num.setError("Field can't be empty");
            } else if (year < cal.get(Calendar.YEAR) || (year == cal.get(Calendar.YEAR) && month <= cal.get(Calendar.MONTH)) || year > cal.get(Calendar.YEAR) + 20) {
                card_exp_date.setError("Check expired Date");
            } else {
                card_exp_date.setError(null);
                result = true;
            }

        } catch (Exception e) {
            card_exp_date.setError("Expired Date should be MM/YY ");
        }
        return result;
    }

    //check validate credit card CVV
    private boolean validateCVV() {
        String card_cvv_input = card_cvv.getText().toString().trim();
        boolean result = false;
        if (card_cvv_input.length() == 0) {
            card_cvv.setError("Field can't be empty");
        } else if (card_cvv_input.length() < 3) {
            card_cvv.setError("Field CVV should 3 digits");
        } else {
            card_cvv.setError(null);
            result = true;
        }
        return result;
    }

    //Save the credit card information
    public void doSave(View view) {
        //if any information is not correct then
        if (!validateCardName() | !validateCardNum() | !validateExpDate() | !validateCVV() ) {
            return;
        } else {
            //get all the values in String
            String card_num_input = card_num.getText().toString().trim();

            Intent intent = new Intent(this, PayPalPayment.class);
            intent.putExtra("card_num", card_num_input);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Success save", Toast.LENGTH_SHORT).show();

        }
    }



}