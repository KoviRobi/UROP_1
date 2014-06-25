/**
 * 
 */
package com.github.KoviRobi;

import static org.junit.Assert.*;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;
import com.mongodb.MongoTimeoutException;

/**
 * @author kr2
 *
 */
public class UserActionsTest {

	@Test
	public void test() {
        UserInterface testInstance = null;
            
        try
        {
            testInstance = UserInterface.getTestInstance();
        }
        catch (NoSuchAlgorithmException e)
        {
            String error = "Not able to use SHA-512!";
            fail(error);
        }
        catch (UnknownHostException e)
        {
            String error = "Not able to connect to MongoDB!" + e.getMessage();
            fail(error);
        }
        catch (MongoTimeoutException e)
        {
            String error = "Connection to database timed out!";
            fail(error);
        }

        assertNotNull("testInstance should not have been null!");

        // Add
        try
        {
            testInstance.addUser("foo", "bar");
        }
        catch (UserExistanceException e)
        {
            fail("User \"foo\" already exists in MongoDB db: test, collection: users!");
        }

        try
        {
            // Deliberate duplicate
            testInstance.addUser("foo", "bar");
            fail("Should have thrown a \"UserExistanceException\" exception when adding an existing user!");
        }
        catch (UserExistanceException e)
        {
            // Want this exception to happen!
            // Deliberately empty
        }

        // Login
        assertTrue("Failed to authenticate with user \"foo\" with the right password!", testInstance.authenticateUser("foo", "bar"));
        assertFalse("Authenticated \"foo\" with the wrong password!", testInstance.authenticateUser("foo", "baz"));

        // Delete

        try
        {
            testInstance.delUser("foo");
        }
        catch (UserExistanceException e)
        {
            fail ("Failed to remove user \"foo\"!");
        }

        try
        {
            // Deliberate duplicate
            testInstance.delUser("foo");
            fail("Should have thrown a \"UserExistanceException\" exception when removing a non-existing user!");
        }
        catch (UserExistanceException e)
        {
            // Want this exception to happen!
            // Deliberately empty
        }
	}

}
