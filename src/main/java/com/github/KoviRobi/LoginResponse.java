package com.github.KoviRobi;

public class LoginResponse {
    String authenticationCookie = "none";
    boolean hasAuthenticated;

    public boolean isAuthenticated () { return hasAuthenticated; }
    public void setAuthenticated (boolean value) { hasAuthenticated = value; }
    public String getAuthenticationCookie() { return authenticationCookie; }
    public void setAuthenticationCookie(String value) { authenticationCookie = value; }

    LoginResponse ( boolean hasAuthenticated )
    {
        this.hasAuthenticated = hasAuthenticated;
    }

    LoginResponse ( boolean hasAuthenticated, String cookie )
    {
        this.hasAuthenticated = hasAuthenticated;
        authenticationCookie = cookie;
    }
}
