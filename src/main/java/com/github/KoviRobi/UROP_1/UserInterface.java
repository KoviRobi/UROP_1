package com.github.KoviRobi.UROP_1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.net.UnknownHostException;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

// Also a singleton class, as we need to throw UnknownHostException at some point,
// but not sure how exceptions and static work together TODO:<-
public class UserInterface {
    private static UserInterface instance;
    MessageDigest md;
    DBCollection usersCollection;

    // {{{ TODO: Is this the best way? Also, time to live? Also, stale tokens?
    static Map<Long, String> AuthenticatedUsers = new HashMap<Long, String>();
    static Random rand = new Random();

    static List<String> getUsers ()
    {
        return new LinkedList<String>(AuthenticatedUsers.values());
    }

    static String getUser (long token)
    {
        return AuthenticatedUsers.get(token);
    }

    static long setUser (String username)
    {
        long token = rand.nextLong();
        
        while (AuthenticatedUsers.containsKey(token))
            token = rand.nextLong();

        AuthenticatedUsers.put(token, username);
        return token;
    }
    // }}}

    private UserInterface (String dbName, String collectionName) throws UnknownHostException, NoSuchAlgorithmException
    { // REQUIRES: Database and collection in that database must exist
        DB db = MongoDB.connect().getDB(dbName);
        usersCollection = db.getCollection(collectionName);
        md = MessageDigest.getInstance("SHA-512");
    }

    public static UserInterface getInstance () throws UnknownHostException, NoSuchAlgorithmException
    {
        if (instance == null)
            instance = new UserInterface ("local", "users");
        return instance;

    }

    public boolean authenticateUser (String username, String password)
    {   // Simple database query
        md.reset();
        DBObject query = new BasicDBObject("username", username).append
            ("password", md.digest(password.getBytes()));
        DBObject result = usersCollection.findOne(query);
        if (result != null)
        {
            return true;
        }
        return false;
    }

    public void addUser (String username, String password) throws UserExistanceException
    {
        BasicDBObject newUser = new BasicDBObject("username", username);

        // If there is already a user by such name
        if (usersCollection.findOne(newUser) != null)
            throw new UserExistanceException(username);

        // Otherwise build our user object
        md.reset();
        newUser = newUser.append("password", md.digest(password.getBytes()));
        usersCollection.insert(newUser);
    }
    
    public void delUser (String username) throws UserExistanceException
    {
        BasicDBObject removeableUser = new BasicDBObject("username", username);

        // If there is already a user by such name
        if (usersCollection.findOne(removeableUser) == null)
            throw new UserExistanceException(username);

        usersCollection.remove(removeableUser);
    }

    // {{{ DEBUG:
    static UserInterface testInstance;
    static UserInterface getTestInstance () throws UnknownHostException, NoSuchAlgorithmException
    {
        if (testInstance == null)
            testInstance = new UserInterface ("test", "users");
        return testInstance;
    }
    // }}}
}
