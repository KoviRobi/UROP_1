package com.github.KoviRobi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

// Class to hold JSON (Jackson2) data
public class LoginResponse implements ResponseType {
    @JsonProperty("authenticationCookie") String authenticationCookie;

    @JsonCreator public LoginResponse
        (@JsonProperty("authenticationCookie") String cookie)
        { authenticationCookie = cookie; }
}
