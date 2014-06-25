package com.github.KoviRobi;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.net.UnknownHostException;

// Also a singleton class, as we need to throw UnknownHostException at some point,
// but not sure how exceptions and static work together TODO:<-
public class User {
    private static User instance;
    MessageDigest md;
    DBCollection usersCollection;

    private User (String dbName, String collectionName) throws UnknownHostException, NoSuchAlgorithmException
    { // REQUIRES: Database and collection in that database must exist
        DB db = MongoDB.connect().getDB(dbName);
        usersCollection = db.getCollection(collectionName);
        md = MessageDigest.getInstance("SHA-512");
    }

    public static User getInstance () throws UnknownHostException, NoSuchAlgorithmException
    {
        if (instance == null)
            instance = new User ("test", "users");
        return instance;

    }

    public boolean authenticateUser (String username, String password)
    {   // Simple database query
        md.reset();
        DBObject query = new BasicDBObject("username", username).append
            ("password", md.digest(password.getBytes()));
        DBObject result = usersCollection.findOne(query);
        if (result != null)
            return true;
        return false;
    }

    public void addUser (String username, String password) throws UserExistsException
    {
        BasicDBObject newUser = new BasicDBObject("username", username);

        // If there is already a user by such name
        if (usersCollection.findOne(newUser) != null)
            throw new UserExistsException(username);

        // Otherwise build our user object
        md.reset();
        newUser = newUser.append("password", md.digest(password.getBytes()));
        usersCollection.insert(newUser);
    }
}
