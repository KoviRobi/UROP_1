package com.github.KoviRobi;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;

// Singleton wrapper for a database connection
public class MongoDB {
    private static MongoClient instance; // Single connection

    public static MongoClient connect () throws UnknownHostException
    {
        if (instance == null)
            instance = new MongoClient();
        return instance;
    }

    public static void disconnect ()
    {
        instance.close();
        instance = null;
    }
}
