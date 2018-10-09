package com.app.quandoo.View.Adapter;

import android.databinding.BindingAdapter;
import android.view.View;

public class CustomBindingAdapter
{
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}