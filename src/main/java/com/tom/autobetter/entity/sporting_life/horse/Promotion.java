package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Promotion {

    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "subtitle")
    private String subtitle;
    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
    @JsonProperty(value = "external_link")
    private Boolean hasExternalLink;
    @JsonProperty(value = "image")
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getHasExternalLink() {
        return hasExternalLink;
    }

    public void setHasExternalLink(Boolean hasExternalLink) {
        this.hasExternalLink = hasExternalLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
