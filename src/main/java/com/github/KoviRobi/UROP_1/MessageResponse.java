package com.github.KoviRobi.UROP_1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

// Class to hold JSON (Jackson2) data
public class MessageResponse implements ResponseType {
    @JsonProperty("message") String message;

    @JsonCreator public MessageResponse
        (@JsonProperty("message") String message)
        { this.message = message; }
}
