package com.vicky7230.eatit.ui.home.Imagga;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;
import com.vicky7230.eatit.BuildConfig;
import com.vicky7230.eatit.R;
import com.vicky7230.eatit.di.component.ApplicationComponent;
import com.vicky7230.eatit.ui.base.BaseFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImaggaFragment extends BaseFragment implements ImaggaMvpView {

    private static final int REQUEST_IMAGE_CAPTURE = 104;
    private static final int REQUEST_EXTERNAL_STORAGE_READ_ACCESS = 145;
    @Inject
    ImaggaPresenter<ImaggaMvpView> presenter;

    @BindView(R.id.camera_button)
    IconTextView cameraButton;

    String mCurrentPhotoPath;

    public static ImaggaFragment newInstance() {
        Bundle args = new Bundle();
        ImaggaFragment fragment = new ImaggaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imagga, container, false);
        presenter.onAttach(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick(R.id.camera_button)
    void onCameraButtonClick(View view) {
        if (hasPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})) {
            try {
                openCamera();
            } catch (IOException e) {
                Timber.e(e);
            }
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_READ_ACCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_READ_ACCESS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        openCamera();
                    } catch (IOException e) {
                        Timber.e(e);
                    }
                } else {
                    getBaseActivity().showMessage("Permission Denied.");
                }
                break;
            default:
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void openCamera() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException exception) {
                // Error occurred while creating the File
                Timber.e(exception);
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());

            InputStream ims = null;
            try {
                ims = new FileInputStream(file);
                Bitmap imageBitmap = BitmapFactory.decodeStream(ims);
                if (imageBitmap != null) {
                    presenter.uploadImageToImagga(imageUri.getPath());
                } else {
                    getBaseActivity().showMessage("Empty bitmap.");
                }
            } catch (FileNotFoundException e) {
                Timber.e(e);
            }

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
