package com.github.KoviRobi.UROP_1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

// Class to hold JSON (Jackson2) data
class UserResponse implements ResponseType {
    @JsonProperty("username") String username;
    @JsonProperty("password") String password;

    @JsonCreator public UserResponse
        (@JsonProperty("username") String username
        ,@JsonProperty("password") String password)
        { this.username = username;
          this.password = password;
        }

}
