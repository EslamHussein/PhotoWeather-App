package com.robusta.photoweatherapp.model.dto;

import android.graphics.Bitmap;

/**
 * Created by Eslam Hussein on 10/28/17.
 */

public class Photo {
    private String name;
    private String fullPath;
    private Bitmap bitmap;

    public Photo(String name, String fullPath, Bitmap bitmap) {
        this.name = name;
        this.fullPath = fullPath;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
