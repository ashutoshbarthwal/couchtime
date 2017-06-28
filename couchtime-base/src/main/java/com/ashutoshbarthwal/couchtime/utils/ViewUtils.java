package com.ashutoshbarthwal.couchtime.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ashutosh on 04-03-2017.
 */
public class ViewUtils {

    public static void showToast(String message, Context context) {
//        showMessageInToast(message, context);
    }

    private static void showMessageInToast(String message, Context ctx) {
        if (ctx != null)
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
