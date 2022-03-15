package comp3350.sceneit.data;

import org.junit.Test;

import java.util.UUID;

import comp3350.sceneit.data.exceptions.InvalidUserCredentials;
import comp3350.sceneit.data.exceptions.UserExistsException;
import comp3350.sceneit.data.exceptions.UserManagerException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HTTPUserManagerTest {
    @Test
    public void testRegisterLogin() {
        String username = "test_"+ UUID.randomUUID().toString();
        String password = "testpassword";
        String name = "Test User";
        String email = "test@email.ca";

        UserManager um = new HTTPUserManager();
        try {
            // Register
            User u = um.register(username, password, name, email);
            assertEquals(u.getName(), name);
            assertEquals(u.getUsername(), username);
            assertEquals(u.getEmail(), email);

            // Login
            u = um.login(username, password);
            assertEquals(u.getName(), name);
            assertEquals(u.getUsername(), username);
            assertEquals(u.getEmail(), email);
        } catch (UserExistsException e) {
            e.printStackTrace();
            fail(String.format("Error user already exists: %s", e.getMessage()));
        } catch (UserManagerException e) {
            e.printStackTrace();
            fail(String.format("Error user exception: %s", e.getMessage()));
        } catch (InvalidUserCredentials e) {
            fail(String.format("Error invalid credentials: %s", e.getMessage()));
            e.printStackTrace();
        }
    }
}
