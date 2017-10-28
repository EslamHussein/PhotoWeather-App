package com.robusta.utils;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.robusta.photoweatherapp.R;

/**
 * Created by Eslam Hussein on a10/19/2016.
 */

public class DialogUtils {

    public static ProgressDialog getProgressDialog(
            Context context, String message, boolean canCancelable,
            boolean canceledOnTouchOutside) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(canCancelable);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static Dialog showFullImageDialog(Context context, Bitmap bitmap) {

        Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.image_full_size_layout
                , null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_photo);
        imageView.setImageBitmap(bitmap);
        settingsDialog.setContentView(view);

        return settingsDialog;

    }

    public static Dialog showFullImageDialog(Context context, @DrawableRes int image) {

        Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.image_full_size_layout
                , null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_photo);
        imageView.setImageResource(image);
        dialog.setContentView(view);

        return dialog;

    }


}
