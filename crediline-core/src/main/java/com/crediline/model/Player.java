package com.crediline.model;

/**
 * Created by dimer on 2/9/14.
 */
public class Player {
    private String name;
    private int i;
    private String pictureLink;

    public Player(String name, int i, String pictureLink) {
        this.name = name;
        this.i = i;
        this.pictureLink = pictureLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
