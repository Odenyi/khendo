package com.streaming.khendo;

public class Upload {
    private String mName;
    private String mProfile;
    private String mImageUrl;

    public Upload() {
        //empty constructor needed
    }

    public String getmProfile() {
        return mProfile;
    }

    public void setmProfile(String mProfile) {
        this.mProfile = mProfile;
    }

    public Upload(String name,String profile,  String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mProfile =profile;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
