package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reference {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "external_reference")
    private List<ExternalReference> externalReference;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ExternalReference> getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(List<ExternalReference> externalReference) {
        this.externalReference = externalReference;
    }
}
