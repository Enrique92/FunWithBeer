package com.example.kike.funwithbeers.models;

public class Brand {
    private int id;
    private String name;
    private String founded;
    private String country;
    private String description;
    private float lat;
    private float log;
    private String linkImageBrand;


    public Brand(int id, String name, String founded, String country, String description, float lat, float log, String linkImageBrand) {
        this.id = id;
        this.name = name;
        this.founded = founded;
        this.country = country;
        this.description = description;
        this.lat = lat;
        this.log = log;
        this.linkImageBrand = linkImageBrand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkImageBrand() {
        return linkImageBrand;
    }

    public void setLinkImageBrand(String linkImageBrand) {
        this.linkImageBrand = linkImageBrand;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLog() {
        return log;
    }

    public void setLog(float log) {
        this.log = log;
    }
}
