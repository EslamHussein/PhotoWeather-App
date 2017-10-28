package com.robusta.photoweatherapp.model.file;

import android.graphics.Bitmap;

import com.robusta.photoweatherapp.model.dto.Photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Eslam Hussein on 10/28/17.
 */

public interface PhotoFileRepo {

    Boolean saveImage(final Bitmap bitmap) throws IOException;

    File[] getHistory();

    File getImage(Photo photo) throws FileNotFoundException;

}
