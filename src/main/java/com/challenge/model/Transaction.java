package com.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class Transaction {

    @NonNull
    @JsonProperty("amount")
    private Double amount;

    @NonNull
    @JsonProperty("type")
    private String type;

    @JsonProperty("parent_id")
    private Long parentId;

}
