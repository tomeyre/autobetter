package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video {

    @JsonProperty(value = "media_reference")
    private Reference mediaReference;
    @JsonProperty(value = "default_caption")
    private String defaultCaption;
    @JsonProperty(value = "origin")
    private String origin;
    @JsonProperty(value = "artefacts")
    private List<Artefact> artefacts;
    @JsonProperty(value = "tags")
    private List<String> tags;
    @JsonProperty(value = "base_uri")
    private String baseUri;

    public Reference getMediaReference() {
        return mediaReference;
    }

    public void setMediaReference(Reference mediaReference) {
        this.mediaReference = mediaReference;
    }

    public String getDefaultCaption() {
        return defaultCaption;
    }

    public void setDefaultCaption(String defaultCaption) {
        this.defaultCaption = defaultCaption;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }
}
