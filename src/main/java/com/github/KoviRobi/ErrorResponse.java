package com.github.KoviRobi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

// Class to hold JSON (Jackson2) data
public class ErrorResponse implements ResponseType {
    @JsonProperty("error") String error;

    @JsonCreator public ErrorResponse
        (@JsonProperty("error") String error)
        { this.error = error; }

}
