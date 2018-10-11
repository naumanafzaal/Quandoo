package com.app.quandoo.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Nauman Afzaal on 11/10/2018.
 */
public class Utils
{
    public static void hideKeyboard(View view)
    {
        try
        {
            if (view != null)
            {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
