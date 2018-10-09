package com.app.quandoo.View.Adapter;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.app.quandoo.QuandooApp;
import com.app.quandoo.R;

public class CustomBindingAdapter
{
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("bookedText")
    public static void setBookedText(TextView textView, boolean isBooked)
    {
        if(isBooked)
        {
            textView.setText("Booked");
            textView.setTextColor(ContextCompat.getColor(QuandooApp.context, R.color.colorAccent));
        }
        else
        {
            textView.setText("Empty");
            textView.setTextColor(ContextCompat.getColor(QuandooApp.context, R.color.gray));
        }
    }
}