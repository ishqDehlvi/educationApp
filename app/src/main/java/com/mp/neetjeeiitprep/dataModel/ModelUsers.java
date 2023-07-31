package com.mp.neetjeeiitprep.dataModel;

public class ModelUsers {
    public String getUid() {
        return user_id;
    }

    public void setUid(String user_id) {
        this.user_id = user_id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    String user_id;
    String image;
    String name;
}
