package com.github.KoviRobi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponse implements Response {
    @JsonProperty("error") String error;

    @JsonCreator public ErrorResponse
        (@JsonProperty("error") String error)
        { this.error = error; }

}
