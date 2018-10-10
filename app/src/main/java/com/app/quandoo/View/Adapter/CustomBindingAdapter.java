package com.app.quandoo.View.Adapter;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.app.quandoo.QuandooApp;
import com.app.quandoo.R;
import com.app.quandoo.View.Callback.OnSearchInSoftKeyboardListener;

public class CustomBindingAdapter
{
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show)
    {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("bookedText")
    public static void setBookedText(TextView textView, boolean isBooked)
    {
        if (isBooked)
        {
            textView.setText("Booked");
            textView.setTextColor(ContextCompat.getColor(QuandooApp.context, R.color.colorAccent));
        } else
        {
            textView.setText("Empty");
            textView.setTextColor(ContextCompat.getColor(QuandooApp.context, R.color.gray));
        }

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                return false;
            }
        });
    }

    @BindingAdapter("onSearchInSoftKeyboard") // I like it to match the listener method name
    public static void setOnOkInSoftKeyboardListener(TextView view,
                                                     final OnSearchInSoftKeyboardListener listener)
    {
        if (listener == null)
        {
            view.setOnEditorActionListener(null);
        } else
        {
            view.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    {
                        listener.onSearchPressed(view.getText().toString());
                    }
                    return false;
                }
            });
        }
    }
}