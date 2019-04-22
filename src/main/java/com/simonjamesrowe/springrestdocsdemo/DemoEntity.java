package com.simonjamesrowe.springrestdocsdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
public class DemoEntity {


    private Integer id;

    private String aString;

    private List<String> values;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("a_string")
    public String getaString() {
        return aString;
    }

    @JsonProperty("values")
    public List<String> getValues() {
        return values;
    }
}
