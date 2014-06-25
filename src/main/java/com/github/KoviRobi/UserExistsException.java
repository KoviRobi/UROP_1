package com.github.KoviRobi;

public class UserExistsException extends Exception {
    String username;

    public UserExistsException (String username)
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
