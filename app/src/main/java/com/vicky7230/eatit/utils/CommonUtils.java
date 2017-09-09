package com.vicky7230.eatit.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.vicky7230.eatit.R;

/**
 * Created by agrim on 4/7/17.
 */

public class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static Dialog showLoadingDialog(Context context) {

        Dialog progressDialog = new Dialog(context);

        progressDialog.show();

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;

    }
}
