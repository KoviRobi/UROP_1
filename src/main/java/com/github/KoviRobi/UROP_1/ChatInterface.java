package com.github.KoviRobi.UROP_1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import java.net.UnknownHostException;

import java.util.List;
import java.util.ArrayList;

public class ChatInterface {
    private static ChatInterface instance;
    DBCollection chatCollection;

    private ChatInterface (String dbName, String collectionName) throws UnknownHostException
    { // REQUIRES: Database and collection in that database must exist
        DB db = MongoDB.connect().getDB(dbName);
        chatCollection = db.getCollection(collectionName);
    }

    public static ChatInterface getInstance () throws UnknownHostException
    {
        if (instance == null)
            instance = new ChatInterface ("local", "chat");
        return instance;

    }

    public List<DBObject> getMessages (long time)
    {
        List<DBObject> rtn = new ArrayList<DBObject>();

        DBObject query = new BasicDBObject("time", new BasicDBObject("$gt", time));
        DBCursor cursor = chatCollection.find(query);

        if (cursor.curr() != null)
            rtn.add(cursor.curr());
        while (cursor.hasNext())
        {
            rtn.add(cursor.next());
        }

        return rtn;
    }

    public void sendMessage (String username, String message)
    {
        DBObject query = new BasicDBObject("time", System.currentTimeMillis())
            .append("username", username)
            .append("message", message);
        chatCollection.insert(query);
    }

    // {{{ DEBUG:
    static ChatInterface testInstance;
    static ChatInterface getTestInstance () throws UnknownHostException
    {
        if (testInstance == null)
            testInstance = new ChatInterface ("test", "chat");
        return testInstance;
    }
    // }}}
}
