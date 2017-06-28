package com.ashutoshbarthwal.couchtime.core;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import com.ashutoshbarthwal.couchtime.ui.activities.HomeActivity;
import com.ashutoshbarthwal.couchtime.ui.activities.MovieDetailsActivity;
import com.ashutoshbarthwal.couchtime.utils.NetworkUtils;

/**
 * Created by Ashutosh on 04-03-2017.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showProgressDialog() {
        ((BaseActivity) getActivity()).showProgressDialog();
    }

    public void hideProgressDialog() {
//        ((BaseActivity) getActivity()).hideProgressDialog();
    }

    public boolean isInternetAvailable() {
        return NetworkUtils.isNetworkConnected(getActivity());
    }

    public void showSnackBar(String value) {
        ((BaseActivity) getActivity()).showSnackBar(value);
    }

    public void showSnackBar(int value) {
        ((BaseActivity) getActivity()).showSnackBar(value);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((BaseActivity) getActivity()).getCoordinatorLayout();
    }
}
