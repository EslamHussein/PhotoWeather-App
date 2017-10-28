package com.robusta.photoweatherapp.model.file;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.robusta.photoweatherapp.model.dto.Photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public class PhotoFileRepoImpl implements PhotoFileRepo {
    public static final String TAG = "PhotoFileRepoImpl";

    @Override
    public Boolean saveImage(final Bitmap bitmap) throws IOException {

        Boolean result = true;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/photoweather");
        myDir.mkdirs();

        String fname = "Image-" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        File file = new File(myDir, fname);
        Log.i(TAG, "" + file);
        if (file.exists()) {
            file.delete();

        }
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        out.flush();
        out.close();

        return result;
    }

    @Override
    public File[] getHistory() {
        String path = Environment.getExternalStorageDirectory().toString() + "/photoweather";
        File directory = new File(path);
        return directory.listFiles();
    }

    @Override
    public File getImage(Photo photo) throws FileNotFoundException {
        File imgFile = new File(photo.getFullPath());

        if (!imgFile.exists()) {
            throw new FileNotFoundException();
        }

        return imgFile;
    }

}

