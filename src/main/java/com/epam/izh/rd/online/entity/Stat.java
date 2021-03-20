package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stat {
    @JsonProperty("base_stat")
    private short stat;
}
