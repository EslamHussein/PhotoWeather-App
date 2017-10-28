package com.robusta.photoweatherapp.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.robusta.base.view.BaseActivity;
import com.robusta.photoweatherapp.R;
import com.robusta.photoweatherapp.model.Injection.Injection;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;
import com.robusta.photoweatherapp.ui.adapter.PhotosAdapter;
import com.robusta.photoweatherapp.ui.presenter.PhotoWeatherPresenter;
import com.robusta.photoweatherapp.ui.presenter.PhotoWeatherPresenterImpl;
import com.robusta.photoweatherapp.ui.view.PhotoWeatherView;
import com.robusta.utils.DialogUtils;
import com.robusta.utils.OnClickPhoto;
import com.robusta.utils.TextUtils;

import java.util.List;

public class MainActivity extends BaseActivity<PhotoWeatherPresenter> implements PhotoWeatherView, LocationListener, OnClickPhoto, MultiplePermissionsListener, View.OnClickListener {

    private LocationManager locationManager;
    private Location currentLocation;
    private LinearLayout loadingLayout;
    private TextView loadingTextView;
    private TextView errorTextView;
    private RecyclerView historyRecyclerView;
    private ProgressDialog progressDialog;
    private WeatherResponse weatherInfo;

    private FloatingActionButton cameraFAB;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static int RESULT_IMAGE_CLICK = 1;
    private Uri outPutFileUri;


    @Override
    protected PhotoWeatherPresenter createPresenter() {
        return new PhotoWeatherPresenterImpl(Injection.providePhotoWeatherBusiness(
                Injection.provideWeatherCloudRepoImpl(),
                Injection.providePhotoFileRepoImpl()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingLayout = (LinearLayout) findViewById(R.id.layout_loading);
        errorTextView = (TextView) findViewById(R.id.text_view_error);
        cameraFAB = (FloatingActionButton) findViewById(R.id.fab_camera);
        loadingTextView = (TextView) findViewById(R.id.text_view_loading);
        historyRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_history);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        cameraFAB.setOnClickListener(this);

        Dexter.withActivity(this)
                .withPermissions(PERMISSIONS).withListener(this).check();
    }


    @SuppressWarnings("MissingPermission")
    private void getCurrentLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE_CLICK) {
                getPresenter().prepareImageToShare(weatherInfo, outPutFileUri);
            }
        }
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showBlockingLoading() {

        if (progressDialog == null)
            progressDialog = DialogUtils.getProgressDialog(this, TextUtils.getString(R.string.loading), false, false);
        progressDialog.show();

    }

    @Override
    public void hideBlockingLoading() {

        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;

    }

    @Override
    public void showInlineError(String error) {

        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(TextUtils.getString(R.string.permission_denied));

    }

    @Override
    public void showWeatherData(WeatherResponse weatherResponse) {
        weatherInfo = weatherResponse;
    }

    @Override
    public void enableCamera() {
        cameraFAB.show();
    }


    @Override
    public void shareImageToFaceBook(Bitmap bitmap) {


        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

    }

    @Override
    public void showPermissionDenied() {
        cameraFAB.hide();
        loadingLayout.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        historyRecyclerView.setVisibility(View.GONE);
        errorTextView.setText(TextUtils.getString(R.string.permission_denied));

    }

    @Override
    public void showImagesHistory(List<Photo> photos) {

        int numberOfColumns = 4;
        historyRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        PhotosAdapter adapter = new PhotosAdapter(photos, this);
        historyRecyclerView.setAdapter(adapter);
        historyRecyclerView.setVisibility(View.VISIBLE);


    }

    @Override
    public void showImageFullSize(Bitmap bitmap) {
        DialogUtils.showFullImageDialog(this, bitmap).show();


    }

    @Override
    public void showImageFullSizeError(int tempImageResId) {
        DialogUtils.showFullImageDialog(this, tempImageResId).show();


    }

    @Override
    public void onLocationChanged(Location location) {

        if (currentLocation == null) {
            loadingTextView.setText(TextUtils.getString(R.string.loading_weather));
            currentLocation = location;
            getPresenter().loadWeather(String.valueOf(currentLocation.getLatitude()),
                    String.valueOf(currentLocation.getLongitude()));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void click(Photo photo) {

        getPresenter().getImage(photo);
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            showLoading();
            getCurrentLocation();
            getPresenter().getImageHistory();
        } else {
            showPermissionDenied();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        showPermissionDenied();
        token.continuePermissionRequest();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_camera:

                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                outPutFileUri = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutFileUri);
                startActivityForResult(captureIntent, RESULT_IMAGE_CLICK);

                break;
        }
    }
}
