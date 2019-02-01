package com.example.kike.funwithbeers.models;

public class Continent {
    private int id;
    private String name;
    private String shortName;
    private int image;

    public Continent(int mId, String mName, String mShortName, int mImage) {
        id = mId;
        name = mName;
        shortName = mShortName;
        image = mImage;
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

    public int getImage() {
        return image;
    }

    public void setImage(int mImage) {
        image = mImage;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String mShortName) {
        shortName = mShortName;
    }
}
