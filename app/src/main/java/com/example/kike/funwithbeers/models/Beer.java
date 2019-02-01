package com.example.kike.funwithbeers.models;

public class Beer {
    private int id;
    private String name;
    private String country;
    private String type;
    // % of Alcohol
    private String ABV;
    private String description;
    private float score;
    private String linkimage;

    public Beer(int id, String name, String country, String type, String ABV, String description, float score, String linkimage) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.type = type;
        this.ABV = ABV;
        this.description = description;
        this.score = score;
        this.linkimage = linkimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String mCountry) {
        country = mCountry;
    }

    public String getType() {
        return type;
    }

    public void setType(String mType) {
        type = mType;
    }

    public String getABV() {
        return ABV;
    }

    public void setABV(String ABV) {
        this.ABV = ABV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        description = mDescription;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float mScore) {
        score = mScore;
    }

    public String getLinkimage() {
        return linkimage;
    }

    public void setLinkimage(String mLinkimage) {
        linkimage = mLinkimage;
    }
}
