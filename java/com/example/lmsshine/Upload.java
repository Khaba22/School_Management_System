package com.example.lmsshine;

public class Upload {

    private String mName, fileUri, sName;

    public Upload () {
        // Empty constructor
    }

    public Upload(String name, String imageUrl) {

        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        fileUri = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return fileUri;
    }

    public void setImageUrl(String mImageUrl) {
        fileUri = mImageUrl;
    }
}
