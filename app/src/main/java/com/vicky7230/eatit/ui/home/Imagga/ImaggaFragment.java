package com.vicky7230.eatit.ui.home.Imagga;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joanzapata.iconify.widget.IconTextView;
import com.vicky7230.eatit.R;
import com.vicky7230.eatit.di.component.ActivityComponent;
import com.vicky7230.eatit.ui.base.BaseFragment;
import com.vicky7230.eatit.utils.FileUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.clicked_image)
    ImageView imageView;

    public static ImaggaFragment newInstance() {
        Bundle args = new Bundle();
        ImaggaFragment fragment = new ImaggaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imagga, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {

            component.inject(this);
            presenter.onAttach(this);
            ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick(R.id.camera_button)
    void onCameraButtonClick(View view) {
        if (hasPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})) {
            openCamera();
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_READ_ACCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_READ_ACCESS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    getBaseActivity().showMessage("Permission Denied.");
                }
                break;
            default:
                break;
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (imageBitmap != null) {
                //imageView.setImageBitmap(imageBitmap);
                presenter.uploadImageToImagga(FileUtils.getRealPathFromURI(getContext(), data.getData()));
            } else {
                getBaseActivity().showMessage("Empty bitmap.");
            }
        }
    }
}
