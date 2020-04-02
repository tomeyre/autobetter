package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artefact {

    @JsonProperty(value = "media_instance_reference")
    private String mediaInstanceReference;
    @JsonProperty(value = "mime_type")
    private String mimeType;
    @JsonProperty(value = "uri")
    private String uri;
    @JsonProperty(value = "media_type")
    private String mediaType;
}
