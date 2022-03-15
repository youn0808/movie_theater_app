package comp3350.sceneit.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.sceneit.R;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.HTTPUserManager;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.User;
import comp3350.sceneit.data.UserManager;
import comp3350.sceneit.data.exceptions.InvalidUserCredentials;
import comp3350.sceneit.data.exceptions.UserExistsException;
import comp3350.sceneit.data.exceptions.UserManagerException;

public class SignUpActivity extends AppCompatActivity {

    EditText registerUserName;
    EditText registerUserFullName;
    EditText registerEmail;
    EditText registerPassword;
    EditText registerPasswordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerUserName = (EditText) findViewById(R.id.registerUserName);
        registerUserFullName=(EditText)findViewById(R.id.registerFullName);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerPasswordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);

    }

    public void doSignup(View view) {
        String usernameInput = registerUserName.getText().toString().trim();
        String userFullNameInput = registerUserFullName.getText().toString().trim();
        String userEmailInput = registerEmail.getText().toString().trim();
        String userPasswordInput = registerPassword.getText().toString().trim();
        String userPasswordConfirmInput = registerPasswordConfirm.getText().toString().trim();

        if (validateInfo(usernameInput, userEmailInput, userPasswordInput, userPasswordConfirmInput)) {

            if(addNewUser(usernameInput,userPasswordInput,userFullNameInput,userEmailInput)) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Success registration", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Save data in postgreql on Button click
    private boolean addNewUser(String username, String password, String name, String email) {

        //  TODO check DB
        boolean result=false;
        UserManager um = new HTTPUserManager();
        try {
            um.register(username,password,name,email);
            result = true;
        } catch (UserExistsException | UserManagerException e) {
            Toast.makeText(this,"User already exists",Toast.LENGTH_LONG).show();
        }
         return result;

    }


    private boolean validateInfo(String usernameInput, String userEmailInput,String userPasswordInput,String userPasswordConfirmInput) {
        Boolean validate = true;

        if (usernameInput.isEmpty()) {
            registerUserName.setError("User name cannot be blank");
            validate = false;
        } else {
            registerUserName.setError(null);
        }

        if (userEmailInput.isEmpty()) {
            registerEmail.setError("Email cannot be blank");
            validate = false;
        } else if (!isValidEmail(userEmailInput)) {
            registerEmail.setError("Invalid Email");
            validate = false;
        } else {
            registerEmail.setError(null);
        }

        if (userPasswordInput.isEmpty()) {
            registerPassword.setError("Password cannot be blank");
            validate = false;
        } else if (userPasswordInput.length() < 6) {
            registerPassword.setError("Password must be at least 6 characters long");
            validate = false;
        } else {
            registerPassword.setError(null);
        }

        if (userPasswordConfirmInput.isEmpty()) {
            registerPasswordConfirm.setError("Retyped password cannot be blank");
            validate = false;
        } else if (!userPasswordConfirmInput.matches(userPasswordInput)) {
            registerPasswordConfirm.setError("Retyped and password do not match");
            validate = false;
        } else {
            registerPasswordConfirm.setError(null);
        }

        return validate;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


}