package comp3350.sceneit.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.logic.CreditCard;
import comp3350.sceneit.logic.CreditManager;

public class CreditActivity extends AppCompatActivity implements View.OnClickListener {

    private String message;
    private EditText nameCard, numberCard, cvc, expDate, country, province,
            addressOne, addressTwo, city, postalCode, telephoneNumber, email;
    private TextView purchaseDisplay;
    private Button submitButton;
    private CreditCard myCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        myCard = new CreditCard();
        //initialize buttons, text views, and edit texts
        //All the EditTexts are named the same as their ids
        purchaseDisplay = (TextView) findViewById(R.id.puchaseInfo);
        submitButton = (Button) findViewById(R.id.button);
        nameCard = (EditText) findViewById(R.id.nameCard);
        numberCard = (EditText) findViewById(R.id.numberCard);
        cvc = (EditText) findViewById(R.id.cvc);
        expDate = (EditText) findViewById(R.id.expDate);
        country = (EditText) findViewById(R.id.country);
        province = (EditText) findViewById(R.id.province);
        addressOne = (EditText) findViewById(R.id.addressOne);
        addressTwo = (EditText) findViewById(R.id.addressTwo);
        city = (EditText) findViewById(R.id.city);
        postalCode = (EditText) findViewById(R.id.postalCode);
        telephoneNumber = (EditText) findViewById(R.id.telephoneNumber);
        email = (EditText) findViewById(R.id.email);

        //get stuff from the bundle that was passed
        Bundle b = getIntent().getExtras();
        int price = b.getInt("price");
        int ticketsNum = b.getInt("ticketsNum");
        String movieTitle = b.getString("movieTitle");
        String theatre = b.getString("theatre");

        message = "Purchasing " + ticketsNum + " Tickets for " + movieTitle + " at " + theatre + " for $" + price;
        purchaseDisplay.setText(message);

        submitButton.setOnClickListener(this);

    }

    public void callAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set title
                .setTitle("Error")//Tell the user theres an error in the data
                //set message
                .setMessage(message)//setup with the message that gets passed
                //set the okay button for the user.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //this does nothing but the ok button needs it to work correctly so it exists.
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();//show the alert on screen
    }

    @Override
    public void onClick(View v) {
        //fill our card with the users input and then check if its correct.
        fillCreditCard();
        String error = CreditManager.validateInput(myCard);

        //if the error is null that means no problems were found
        if(error != null){
            callAlert(error);
        } else {

            //This is here for when we link to the next activity. Like confirmation page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    //Fills up the credit card with all the info from the fields the user filled in.
    public void fillCreditCard(){
        myCard.setNumberCard(numberCard.getText().toString());
        myCard.setNameCard(nameCard.getText().toString());
        myCard.setCvc(cvc.getText().toString());
        myCard.setExpDate(expDate.getText().toString());
        myCard.setCountry(country.getText().toString());
        myCard.setProvince(province.getText().toString());
        myCard.setAddressOne(addressOne.getText().toString());
        myCard.setAddressTwo(addressTwo.getText().toString());
        myCard.setCity(city.getText().toString());
        myCard.setPostalCode(postalCode.getText().toString());
        myCard.setTelephoneNumber(telephoneNumber.getText().toString());
        myCard.setEmail(email.getText().toString());
    }
}