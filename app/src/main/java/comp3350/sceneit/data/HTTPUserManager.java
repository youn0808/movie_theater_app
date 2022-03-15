package comp3350.sceneit.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import comp3350.sceneit.data.exceptions.InvalidUserCredentials;
import comp3350.sceneit.data.exceptions.UserManagerAuthException;
import comp3350.sceneit.data.exceptions.UserManagerException;
import comp3350.sceneit.data.exceptions.UserManagerNetworkingException;
import comp3350.sceneit.data.exceptions.UserExistsException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPUserManager implements UserManager{
    private static final String REGISTER_URL = "http://api.sceneit.linney.xyz/auth/register";
    private static final String TOKEN_URL = "http://api.sceneit.linney.xyz/auth/token";
    private static final String USER_DATA = "http://api.sceneit.linney.xyz/auth/users/me";

    private String makeRequestInThread(Request request) throws UserManagerNetworkingException, UserManagerAuthException, UserExistsException {
        AtomicReference<String> response_str = new AtomicReference<>();
        AtomicBoolean genericError = new AtomicBoolean(false);
        AtomicBoolean authError = new AtomicBoolean(false);
        AtomicBoolean invalidCredentials = new AtomicBoolean(false);


        Thread thread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 401) {
                    authError.set(true);
                    return;
                } else if (response.code() == 422) {
                    invalidCredentials.set(true);
                    return;
                }
                if (response.body() != null) {
                    response_str.set(response.body().string());
                } else {
                    response_str.set(null);
                }

            } catch (IOException e) {
                genericError.set(true);
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new UserManagerNetworkingException();
        }

        if (authError.get()) {
            throw new UserManagerAuthException();
        }

        if (genericError.get()) {
            throw new UserManagerNetworkingException();
        }

        if (invalidCredentials.get()) {
            throw new UserExistsException();
        }

        return response_str.get();
    }

    private User getUser(String jwt) throws UserManagerException {
        Request request_user_data = new Request.Builder()
                .url(USER_DATA)
                .addHeader("Authorization", "Bearer "+ jwt)
                .build();

        String response;
        try {
             response = this.makeRequestInThread(request_user_data);
        } catch (UserExistsException e) {
            throw new UserManagerException();
        }

        User user;
        try {
            JSONObject response_data = new JSONObject(response);
            user = new User(response_data.getString("username"),
                    response_data.getString("name"),
                    response_data.getString("email"));
        } catch (JSONException e) {
            throw new UserManagerException();
        }

        return user;
    }

    @Override
    public User register(String username, String password, String name, String email) throws UserExistsException, UserManagerException {
        JSONObject registerDetails = new JSONObject();
        String jwt_str;

        try {
            registerDetails.put("username", username);
            registerDetails.put("password", password);
            registerDetails.put("name", name);
            registerDetails.put("email", email);
        } catch (JSONException e) {
            throw new UserManagerException();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), registerDetails.toString());
        Request request = new Request.Builder()
                .url(REGISTER_URL)
                .post(body)
                .build();

        String response = this.makeRequestInThread(request);
        try {
            JSONObject response_data = new JSONObject(response);
            jwt_str = response_data.getString("access_token");
        } catch (JSONException e) {
            throw new UserManagerException();
        }

        return getUser(jwt_str);
    }

    @Override
    public User login(String username, String password) throws InvalidUserCredentials, UserManagerException {
        String jwt_str;

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .build();

        String response;
        try {
            response = this.makeRequestInThread(request);
        } catch (UserExistsException e) {
            throw new UserManagerException();
        } catch (UserManagerAuthException e) {
            throw new InvalidUserCredentials();
        }

        try {
            JSONObject response_data = new JSONObject(response);
            jwt_str = response_data.getString("access_token");
        } catch (JSONException e) {
            throw new UserManagerException();
        }

        return getUser(jwt_str);
    }
}
