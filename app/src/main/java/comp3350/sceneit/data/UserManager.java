package comp3350.sceneit.data;

import comp3350.sceneit.data.exceptions.InvalidUserCredentials;
import comp3350.sceneit.data.exceptions.UserExistsException;
import comp3350.sceneit.data.exceptions.UserManagerException;

public interface UserManager {
    /**
     * Register a new user
     *
     * @param username The username to use
     * @param password The password to use
     * @param name The users name, Ex. Joe Smoe
     * @param email The users email address
     * @return The logged in users info
     * @throws UserExistsException if the user already exists
     */
    public User register(String username, String password, String name, String email) throws UserExistsException, UserManagerException;

    /**
     * Login as a user
     *
     * @param username The username to use
     * @param password The password to use
     * @return The logged in users info
     * @throws InvalidUserCredentials if the user could not be logged in due to invalid credentials
     */
    public User login(String username, String password) throws InvalidUserCredentials, UserManagerException;
}
