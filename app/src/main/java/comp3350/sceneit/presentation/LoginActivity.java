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

public class LoginActivity extends AppCompatActivity {

    EditText signInUserNames;
    EditText signInUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void loginBtnClicked(View view) {
        signInUserNames = (EditText) findViewById(R.id.signInUserNames);
        signInUserPassword = (EditText) findViewById(R.id.signInUserPassword);

        String userNamesInput = signInUserNames.getText().toString().trim();
        String userPasswordInput = signInUserPassword.getText().toString().trim();
        if(validateAccount(userNamesInput,userPasswordInput)) {
            if(login(userNamesInput,userPasswordInput)){
                Intent intent = new Intent(this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Success login", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        }
    }


    private boolean login(String username, String password) {

//        TODO check DB
        boolean result = false;
        UserManager um = new HTTPUserManager();
        try {
            um.login(username,password);
            result= true;
        } catch (InvalidUserCredentials | UserManagerException e) {
            Toast.makeText(this,"Invalid user credential",Toast.LENGTH_LONG).show();
        }
        return result;
    }


    private boolean validateAccount(String userNames, String password){
        Boolean validate = true;

        if(userNames.isEmpty()){
            signInUserNames.setError("User name cannot be blank");
            validate = false;
        } else{   signInUserNames.setError(null);        }

        if(password.isEmpty()){
            signInUserNames.setError(null);
            signInUserPassword.setError("Password cannot be blank");
            validate = false;
        }
        else{   signInUserPassword.setError(null);    }

        return validate;
    }

    public void signUpClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
}