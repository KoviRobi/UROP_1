package com.github.KoviRobi;

public class UserExistanceException extends Exception {
    String username;

    public UserExistanceException (String username)
    {
        super (username);
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

}
